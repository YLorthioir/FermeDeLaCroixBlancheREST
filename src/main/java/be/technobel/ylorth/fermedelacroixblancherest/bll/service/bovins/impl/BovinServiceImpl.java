package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.BovinService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.InfosEngraissement;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.InfosReproduction;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.Melange;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEngraissementEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.FemelleReproductionEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.BovinUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.BovinUpdateTypeForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BovinServiceImpl implements BovinService {

    private final BovinRepository bovinRepository;
    private final FemelleReproductionRepository femelleReproductionRepository;
    private final RaceRepository raceRepository;
    private final BovinEngraissementRepository bovinEngraissementRepository;
    private final ChampRepository champRepository;
    private final MelangeRepository melangeRepository;

    public BovinServiceImpl(BovinRepository bovinRepository,
                            RaceRepository raceRepository,
                            FemelleReproductionRepository femelleReproductionRepository,
                            BovinEngraissementRepository bovinEngraissementRepository,
                            ChampRepository champRepository,
                            MelangeRepository melangeRepository) {
        this.bovinRepository = bovinRepository;
        this.raceRepository = raceRepository;
        this.femelleReproductionRepository = femelleReproductionRepository;
        this.bovinEngraissementRepository = bovinEngraissementRepository;
        this.champRepository = champRepository;
        this.melangeRepository = melangeRepository;
    }

    /**
     * Récupère un Set de tous les objets Bovin
     *
     * @return un Set de Bovin
     */
    @Override
    public Set<String> getAllNI(){
        return  bovinRepository.findAll().stream()
                .map(BovinEntity::getNumeroInscription)
                .collect(Collectors.toSet());
    }

    /**
     * Récupère un objet Bovin en fonction du numéro d'inscription fourni.
     *
     * @param numeroInscription Le numéro d'inscription du bovin à récupérer.
     * @return L'objet Bovin correspondant au numéro d'inscription spécifié.
     * @throws NotFoundException Si aucun bovin n'est trouvé avec le numéro d'inscription donné.
     */
    @Override
    public Bovin getOne(String numeroInscription) {
        Bovin bovin = bovinRepository.findByNumeroInscription(numeroInscription)
                .map(Bovin::fromBLL)
                .orElseThrow(() -> new NotFoundException("bovin not found"));

        int nbCesarienne = 0;
        for (BovinEntity child : getChildren(numeroInscription)) {
            if(child.isNeCesarienne())
                nbCesarienne++;
        }

        bovin.setNbCesarienne(nbCesarienne);

        return bovin;
    }

    @Override
    public Set<BovinEntity> getChildren(String numeroInscription) {
        if(bovinRepository.findByNumeroInscription(numeroInscription).orElseThrow(()-> new NotFoundException("BovinEntity not found")).getSexe()=='F')
            return bovinRepository.findAllEnfantsMere(numeroInscription).stream()
                    .collect(Collectors.toSet());

        else if (bovinRepository.findByNumeroInscription(numeroInscription).orElseThrow(()-> new NotFoundException("BovinEntity not found")).getSexe()=='M')
            return bovinRepository.findAllEnfantsPere(numeroInscription).stream()
                    .collect(Collectors.toSet());
        else
            return null;

    }


    @Override
    public void createBovin(BovinInsertForm form) {
        if(form == null)
            throw new IllegalArgumentException("le formulaire ne peut être null");

        if(bovinRepository.existsByNumeroInscription(form.getNumeroInscription()))
            throw new AlreadyExistsException("Le numéro d'identification du BovinEntity existe déjà");

        BovinEntity entity = new BovinEntity();
        entity.setNumeroInscription(form.getNumeroInscription());
        entity.setPereNI(form.getPereNI());
        entity.setMereNI(form.getMereNI());
        entity.setNeCesarienne(form.isNeCesarienne());
        entity.setDateDeNaissance(form.getDateDeNaissance());
        entity.setPoidsNaissance(form.getPoidsNaissance());
        entity.setSexe(form.getSexe().charAt(0));
        entity.setRace(raceRepository.findById(form.getRaceId())
                .orElseThrow(() -> new NotFoundException("race not found")));

        entity = bovinRepository.save(entity);
    }

    @Override
    public void updateBovin(Long id,BovinUpdateForm form) {
        if(form == null)
            throw new IllegalArgumentException("le formulaire ne peut être null");

        if(bovinRepository.existsByNumeroInscription(form.getNumeroInscription())&& bovinRepository.findByNumeroInscription(form.getNumeroInscription()).get().getId()!=id)
            throw new AlreadyExistsException("BovinEntity déjà existant");

        if(femelleReproductionRepository.existsById(id)){
            FemelleReproductionEntity entity = new FemelleReproductionEntity(form.getNumeroInscription(),form.getSexe().charAt(0),form.getDateDeNaissance(),form.getPoidsNaissance(),form.isEnCharge(),form.isNeCesarienne(),form.getPereNI(),form.getMereNI(),raceRepository.findById(form.getRaceId()).get(),form.getDerniereInsemination(),form.getPerteGrossesse());
            entity.setNom(form.getNom());
            entity.setId(id);
            entity.setDateAbattage(form.getDateAbattage()==null?null:form.getDateAbattage());
            entity.setRaisonAbattage(form.getRaisonAbattage());
            entity.setChamp(champRepository.findById(form.getChampId()).get());

            femelleReproductionRepository.save(entity);

        } else if(bovinEngraissementRepository.existsById(id)){

            BovinEngraissementEntity entity = new BovinEngraissementEntity(form.getNumeroInscription(),form.getSexe().charAt(0),form.getDateDeNaissance(),form.getPoidsNaissance(),form.isEnCharge(),form.isNeCesarienne(),form.getPereNI(),form.getMereNI(),raceRepository.findById(form.getRaceId()).get(),form.getPoidsSurPattes(),form.getPoidsCarcasse(),form.getDateEngraissement(),melangeRepository.findById(form.getMelangeId()).get(),null);
            entity.setNom(form.getNom());
            entity.setId(id);
            entity.setDateAbattage(form.getDateAbattage()==null?null:form.getDateAbattage());
            entity.setRaisonAbattage(form.getRaisonAbattage());
            entity.setChamp(champRepository.findById(form.getChampId()).get());

            bovinEngraissementRepository.save(entity);


        } else {

        BovinEntity entity = new BovinEntity(form.getNumeroInscription(), form.getSexe().charAt(0),form.getDateDeNaissance(),form.getPoidsNaissance(),form.isEnCharge(),form.isNeCesarienne(),form.getPereNI(),form.getMereNI(),raceRepository.findById(form.getRaceId()).get());
        entity.setNom(form.getNom());
        entity.setId(id);
        entity.setDateAbattage(form.getDateAbattage()==null?null:form.getDateAbattage());
        entity.setRaisonAbattage(form.getRaisonAbattage());
        entity.setChamp(form.getChampId()==null?null:champRepository.findById(form.getChampId()).get());

        bovinRepository.save(entity);
        }

    }

    @Override
    public InfosReproduction getInfosReproduction(Long id) {

        if(femelleReproductionRepository.existsById(id)){

            FemelleReproductionEntity entity = femelleReproductionRepository.findById(id).get();

            int nbCesariennes = 0;

            for (BovinEntity child : getChildren(entity.getNumeroInscription())) {
                if(child.isNeCesarienne())
                    nbCesariennes++;

            }

            return InfosReproduction.builder()
                    .perteGrossesse(entity.getPerteGrossesse())
                    .derniereInsemination(entity.getDerniereInsemination())
                    .nbCesarienne(nbCesariennes)
                    .build();
        }

        return null;
    }

    @Override
    public InfosEngraissement getInfosEngraissement(Long id) {

        if(bovinEngraissementRepository.existsById(id)){

            BovinEngraissementEntity entity = bovinEngraissementRepository.findById(id).get();

            return InfosEngraissement.builder()
                    .melange(Melange.fromBLL(entity.getMelange()))
                    .dateEngraissement(entity.getDateEngraissement())
                    .poidsCarcasse(entity.getPoidsCarcasse())
                    .poidsSurPattes(entity.getPoidsSurPattes())
                    .build();
        }

        return null;
    }

    @Override
    public void updateType(Long id, BovinUpdateTypeForm form) {
       if("BovinEntity".equals(form.getFinalite())||"FemelleReproduction".equals(form.getFinalite())||"BovinEntityEngraissement".equals(form.getFinalite()))
           bovinRepository.changeType(id, form.getFinalite());
    }

    @Override
    public Set<BovinEntity> getAllTaureaux() {
        return bovinRepository.findAllTaureau().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllEngraissement() {
        return bovinEngraissementRepository.findAll().stream()
                .map(BovinEngraissementEntity::getNumeroInscription)
                .collect(Collectors.toSet());
    }
}

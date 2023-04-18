package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.InfosEngraissement;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.InfosReproduction;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.MelangeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.BovinEngraissement;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.FemelleReproduction;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinUpdateTypeForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.BovinService;
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

    @Override
    public Set<String> getAllNI(){
        return  bovinRepository.findAll().stream()
                .map(Bovin::getNumeroInscription)
                .collect(Collectors.toSet());
    }

    @Override
    public BovinDTO getOne(String numeroInscription) {
        BovinDTO bovin = bovinRepository.findByNumeroInscription(numeroInscription)
                .map(BovinDTO::toDTO)
                .orElseThrow(() -> new NotFoundException("bovin not found"));

        int nbCesarienne = 0;
        for (BovinDTO child : getChildren(numeroInscription)) {
            if(child.isNeCesarienne())
                nbCesarienne++;
        }

        bovin.setNbCesarienne(nbCesarienne);

        return bovin;
    }

    @Override
    public Set<BovinDTO> getChildren(String numeroInscription) {
        if(bovinRepository.findByNumeroInscription(numeroInscription).orElseThrow(()-> new NotFoundException("Bovin not found")).getSexe()=='F')
            return bovinRepository.findAllEnfantsMere(numeroInscription).stream()
                    .map(BovinDTO::toDTO)
                    .collect(Collectors.toSet());

        else if (bovinRepository.findByNumeroInscription(numeroInscription).orElseThrow(()-> new NotFoundException("Bovin not found")).getSexe()=='M')
            return bovinRepository.findAllEnfantsPere(numeroInscription).stream()
                    .map(BovinDTO::toDTO)
                    .collect(Collectors.toSet());
        else
            return null;

    }


    @Override
    public void createBovin(BovinInsertForm form) {
        if(form == null)
            throw new IllegalArgumentException("le formulaire ne peut être null");

        Bovin entity = new Bovin();
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

        if(femelleReproductionRepository.existsById(id)){
            FemelleReproduction entity = new FemelleReproduction(form.getNumeroInscription(),form.getSexe().charAt(0),form.getDateDeNaissance(),form.getPoidsNaissance(),form.isEnCharge(),form.isNeCesarienne(),form.getPereNI(),form.getMereNI(),raceRepository.findById(form.getRaceId()).get(),form.getDerniereInsemination(),form.getPerteGrossesse());
            entity.setNom(form.getNom());
            entity.setId(id);
            entity.setDateAbattage(form.getDateAbattage()==null?null:form.getDateAbattage());
            entity.setRaisonAbattage(form.getRaisonAbattage());
            entity.setChamp(champRepository.findById(form.getChampId()).get());

            femelleReproductionRepository.save(entity);

        } else if(bovinEngraissementRepository.existsById(id)){

            BovinEngraissement entity = new BovinEngraissement(form.getNumeroInscription(),form.getSexe().charAt(0),form.getDateDeNaissance(),form.getPoidsNaissance(),form.isEnCharge(),form.isNeCesarienne(),form.getPereNI(),form.getMereNI(),raceRepository.findById(form.getRaceId()).get(),form.getPoidsSurPattes(),form.getPoidsCarcasse(),form.getDateEngraissement(),melangeRepository.findById(form.getMelangeId()).get(),null);
            entity.setNom(form.getNom());
            entity.setId(id);
            entity.setDateAbattage(form.getDateAbattage()==null?null:form.getDateAbattage());
            entity.setRaisonAbattage(form.getRaisonAbattage());
            entity.setChamp(champRepository.findById(form.getChampId()).get());

            bovinEngraissementRepository.save(entity);


        } else {

        Bovin entity = new Bovin(form.getNumeroInscription(), form.getSexe().charAt(0),form.getDateDeNaissance(),form.getPoidsNaissance(),form.isEnCharge(),form.isNeCesarienne(),form.getPereNI(),form.getMereNI(),raceRepository.findById(form.getRaceId()).get());
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

            FemelleReproduction entity = femelleReproductionRepository.findById(id).get();

            int nbCesariennes = 0;

            for (BovinDTO child : getChildren(entity.getNumeroInscription())) {
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

            BovinEngraissement entity = bovinEngraissementRepository.findById(id).get();

            return InfosEngraissement.builder()
                    .melange(MelangeDTO.toDTO(entity.getMelange()))
                    .dateEngraissement(entity.getDateEngraissement())
                    .poidsCarcasse(entity.getPoidsCarcasse())
                    .poidsSurPattes(entity.getPoidsSurPattes())
                    .build();
        }

        return null;
    }

    @Override
    public void updateType(Long id, BovinUpdateTypeForm form) {
       if("Bovin".equals(form.getFinalite())||"FemelleReproduction".equals(form.getFinalite())||"BovinEngraissement".equals(form.getFinalite()))
           bovinRepository.changeType(id, form.getFinalite());
    }

    @Override
    public Set<BovinDTO> getAllTaureaux() {
        return bovinRepository.findAllTaureau().stream()
                .map(BovinDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllEngraissement() {
        return bovinEngraissementRepository.findAll().stream()
                .map(BovinEngraissement::getNumeroInscription)
                .collect(Collectors.toSet());
    }
}

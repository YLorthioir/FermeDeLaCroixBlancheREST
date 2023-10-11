package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.BovinService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEngraissementEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.FemelleReproductionEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.Champ;
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

        return new Bovin(bovin.id(),
                bovin.numeroInscription(),
                bovin.sexe(),
                bovin.dateDeNaissance(),
                bovin.poidsNaissance(),
                bovin.nom(),
                bovin.enCharge(),
                bovin.neCesarienne(),
                bovin.race(),
                bovin.champ(),
                bovin.pereNI(),
                bovin.mereNI(),
                nbCesarienne,
                bovin.dateAbattage(),
                bovin.raisonAbattage());
    }

    /**
     * Récupère l'ensemble des enfants d'un bovin identifié par son numéro d'inscription.
     *
     * Cette méthode recherche un bovin dans le référentiel par son numéro d'inscription,
     * puis vérifie son sexe. En fonction du sexe, elle retourne l'ensemble des enfants
     * de ce bovin en utilisant les méthodes spécifiques du référentiel.
     *
     * @param numeroInscription Le numéro d'inscription du bovin dont on souhaite récupérer les enfants.
     * @return Un ensemble (Set) de BovinEntity représentant les enfants du bovin spécifié.
     * @throws NotFoundException Si aucun bovin n'est trouvé avec le numéro d'inscription spécifié.
     */
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

    /**
     * Crée un nouvel enregistrement de bovin en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode prend un formulaire (BovinInsertForm) en entrée, vérifie que le formulaire n'est pas null,
     * vérifie également que le numéro d'inscription du bovin n'existe pas déjà dans le référentiel, puis crée
     * un nouvel enregistrement de bovin en utilisant les informations fournies dans le formulaire.
     *
     * @param form Le formulaire contenant les informations pour créer un nouveau bovin.
     * @throws IllegalArgumentException Si le formulaire est null.
     * @throws AlreadyExistsException Si le numéro d'inscription du bovin existe déjà dans le référentiel.
     * @throws NotFoundException Si la race spécifiée dans le formulaire n'est pas trouvée dans le référentiel des races.
     */
    @Override
    public void createBovin(BovinInsertForm form) {
        if(form == null)
            throw new IllegalArgumentException("le formulaire ne peut être null");

        if(bovinRepository.existsByNumeroInscription(form.numeroInscription()))
            throw new AlreadyExistsException("Le numéro d'identification du BovinEntity existe déjà");

        BovinEntity entity = new BovinEntity();
        entity.setNumeroInscription(form.numeroInscription());
        entity.setPereNI(form.pereNI());
        entity.setMereNI(form.mereNI());
        entity.setNeCesarienne(form.neCesarienne());
        entity.setDateDeNaissance(form.dateDeNaissance());
        entity.setPoidsNaissance(form.poidsNaissance());
        entity.setSexe(form.sexe().charAt(0));
        entity.setRace(raceRepository.findById(form.raceId())
                .orElseThrow(() -> new NotFoundException("race not found")));

        entity = bovinRepository.save(entity);
    }

    /**
     * Met à jour les informations d'un bovin existant identifié par son ID en utilisant les données fournies dans le formulaire.
     *
     * Cette méthode prend l'ID du bovin à mettre à jour et un formulaire (`BovinUpdateForm`) contenant les nouvelles informations.
     * Elle vérifie que le formulaire n'est pas null, s'assure que le numéro d'inscription spécifié n'est pas déjà associé à un autre bovin
     * (sauf s'il s'agit du même bovin), puis effectue la mise à jour des informations du bovin en fonction de son type (Femelle de reproduction,
     * Bovin d'engraissement ou Bovin générique).
     *
     * @param id L'ID du bovin à mettre à jour.
     * @param form Le formulaire contenant les nouvelles informations pour le bovin.
     * @throws IllegalArgumentException Si le formulaire est null.
     * @throws AlreadyExistsException Si le numéro d'inscription spécifié existe déjà pour un autre bovin (sauf s'il s'agit du même bovin).
     * @throws NotFoundException Si la race ou le champ spécifiés dans le formulaire ne sont pas trouvés dans leurs référentiels respectifs.
     */
    @Override
    public void updateBovin(Long id,BovinUpdateForm form) {
        if(form == null)
            throw new IllegalArgumentException("le formulaire ne peut être null");

        if(bovinRepository.existsByNumeroInscription(form.numeroInscription())&& bovinRepository.findByNumeroInscription(form.numeroInscription()).get().getId()!=id)
            throw new AlreadyExistsException("BovinEntity déjà existant");

        if(femelleReproductionRepository.existsById(id)){
            FemelleReproductionEntity entity = new FemelleReproductionEntity(form.numeroInscription(),form.sexe().charAt(0),form.dateDeNaissance(),form.poidsNaissance(),form.enCharge(),form.neCesarienne(),form.pereNI(),form.mereNI(),raceRepository.findById(form.raceId()).get(),form.derniereInsemination(),form.perteGrossesse());
            entity.setNom(form.nom());
            entity.setId(id);
            entity.setDateAbattage(form.dateAbattage()==null?null:form.dateAbattage());
            entity.setRaisonAbattage(form.raisonAbattage());
            entity.setChamp(champRepository.findById(form.champId()).get());

            femelleReproductionRepository.save(entity);

        } else if(bovinEngraissementRepository.existsById(id)){

            BovinEngraissementEntity entity = new BovinEngraissementEntity(form.numeroInscription(),form.sexe().charAt(0),form.dateDeNaissance(),form.poidsNaissance(),form.enCharge(),form.neCesarienne(),form.pereNI(),form.mereNI(),raceRepository.findById(form.raceId()).get(),form.poidsSurPattes(),form.poidsCarcasse(),form.dateEngraissement(),melangeRepository.findById(form.melangeId()).get(),null);
            entity.setNom(form.nom());
            entity.setId(id);
            entity.setDateAbattage(form.dateAbattage()==null?null:form.dateAbattage());
            entity.setRaisonAbattage(form.raisonAbattage());
            entity.setChamp(champRepository.findById(form.champId()).get());

            bovinEngraissementRepository.save(entity);


        } else {

        BovinEntity entity = new BovinEntity(form.numeroInscription(),form.sexe().charAt(0),form.dateDeNaissance(),form.poidsNaissance(),form.enCharge(),form.neCesarienne(),form.pereNI(),form.mereNI(),raceRepository.findById(form.raceId()).get());
        entity.setNom(form.nom());
        entity.setId(id);
        entity.setDateAbattage(form.dateAbattage()==null?null:form.dateAbattage());
        entity.setRaisonAbattage(form.raisonAbattage());
        entity.setChamp(form.champId()==null?null:champRepository.findById(form.champId()).get());

        bovinRepository.save(entity);
        }

    }

    /**
     * Récupère les informations de reproduction d'un bovin identifié par son ID.
     *
     * Cette méthode prend l'ID d'un bovin en entrée, vérifie s'il s'agit d'une femelle de reproduction en
     * consultant le référentiel des femelles de reproduction, puis récupère les informations de reproduction
     * telles que le nombre de césariennes et d'autres détails relatifs à la reproduction.
     *
     * @param id L'ID du bovin dont on souhaite récupérer les informations de reproduction.
     * @return Un objet InfosReproduction contenant les informations de reproduction du bovin, ou null si le bovin n'est pas une femelle de reproduction.
     */
    @Override
    public InfosReproduction getInfosReproduction(Long id) {

        if(femelleReproductionRepository.existsById(id)){

            FemelleReproductionEntity entity = femelleReproductionRepository.findById(id).get();

            int nbCesariennes = 0;

            for (BovinEntity child : getChildren(entity.getNumeroInscription())) {
                if(child.isNeCesarienne())
                    nbCesariennes++;

            }

            return new InfosReproduction(entity.getDerniereInsemination(),entity.getPerteGrossesse(), nbCesariennes);
        }

        return null;
    }

    /**
     * Récupère les informations d'engraissement d'un bovin identifié par son ID.
     *
     * Cette méthode prend l'ID d'un bovin en entrée, vérifie s'il s'agit d'un bovin d'engraissement en consultant
     * le référentiel des bovins d'engraissement, puis récupère les informations spécifiques à l'engraissement telles
     * que le mélange alimentaire, la date d'engraissement, le poids de carcasse et le poids sur pattes.
     *
     * @param id L'ID du bovin d'engraissement dont on souhaite récupérer les informations d'engraissement.
     * @return Un objet InfosEngraissement contenant les informations d'engraissement du bovin, ou null si le bovin n'est pas un bovin d'engraissement.
     */
    @Override
    public InfosEngraissement getInfosEngraissement(Long id) {

        if(bovinEngraissementRepository.existsById(id)){

            BovinEngraissementEntity entity = bovinEngraissementRepository.findById(id).get();

            return new InfosEngraissement(entity.getPoidsSurPattes(), entity.getPoidsCarcasse(), entity.getDateEngraissement(),Melange.fromBLL(entity.getMelange()));
        }

        return null;
    }

    /**
     * Met à jour le type (finalité) d'un bovin identifié par son ID en utilisant les données fournies dans le formulaire.
     *
     * Cette méthode prend l'ID d'un bovin en entrée, ainsi qu'un formulaire (`BovinUpdateTypeForm`) contenant la nouvelle finalité
     * pour le bovin. Elle vérifie que la finalité spécifiée est l'une des valeurs valides ("BovinEntity", "FemelleReproduction"
     * ou "BovinEntityEngraissement"), puis met à jour le type (finalité) du bovin en conséquence.
     *
     * @param id L'ID du bovin à mettre à jour.
     * @param form Le formulaire contenant la nouvelle finalité pour le bovin.
     */
    @Override
    public void updateType(Long id, BovinUpdateTypeForm form) {
       if("BovinEntity".equals(form.finalite())||"FemelleReproduction".equals(form.finalite())||"BovinEntityEngraissement".equals(form.finalite()))
           bovinRepository.changeType(id, form.finalite());
    }

    /**
     * Récupère l'ensemble des taureaux présents dans le référentiel de bovins.
     *
     * Cette méthode interroge le référentiel de bovins pour obtenir tous les taureaux enregistrés,
     * puis les retourne dans un ensemble (Set) de BovinEntity.
     *
     * @return Un ensemble (Set) de BovinEntity représentant tous les taureaux enregistrés.
     */
    @Override
    public Set<BovinEntity> getAllTaureaux() {
        return bovinRepository.findAllTaureau().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Récupère l'ensemble des numéros d'inscription des bovins d'engraissement présents dans le référentiel.
     *
     * Cette méthode interroge le référentiel des bovins d'engraissement pour obtenir tous les enregistrements,
     * puis extrait les numéros d'inscription de ces enregistrements et les retourne dans un ensemble (Set) de chaînes de caractères.
     *
     * @return Un ensemble (Set) de chaînes de caractères représentant les numéros d'inscription de tous les bovins d'engraissement enregistrés.
     */
    @Override
    public Set<String> getAllEngraissement() {
        return bovinEngraissementRepository.findAll().stream()
                .map(BovinEngraissementEntity::getNumeroInscription)
                .collect(Collectors.toSet());
    }
}

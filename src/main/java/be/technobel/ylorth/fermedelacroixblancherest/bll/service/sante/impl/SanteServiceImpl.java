package be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.models.Vaccination;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante.SanteService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.AForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.VaccinForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.BovinRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SanteServiceImpl implements SanteService {

    private final InjectionRepository injectionRepository;
    private final VaccinRepository vaccinRepository;
    private final ARepository aRepository;
    private final BovinRepository bovinRepository;
    private final MaladieRepository maladieRepository;
    private final TraitementRepository traitementRepository;

    public SanteServiceImpl(InjectionRepository injectionRepository,
                            VaccinRepository vaccinRepository,
                            ARepository aRepository,
                            BovinRepository bovinRepository,
                            MaladieRepository maladieRepository,
                            TraitementRepository traitementRepository) {
        this.injectionRepository = injectionRepository;
        this.vaccinRepository = vaccinRepository;
        this.aRepository = aRepository;
        this.bovinRepository = bovinRepository;
        this.maladieRepository = maladieRepository;
        this.traitementRepository = traitementRepository;
    }

    //-------------------------------- Vaccin ------------------------------------------

    /**
     * Insère une nouvelle injection (vaccination) pour un bovin spécifié par son ID et un nom de vaccin.
     *
     * Cette méthode prend l'ID d'un bovin et le nom d'un vaccin en entrée, puis vérifie si l'injection peut être insérée. Elle récupère le carnet
     * de vaccinations du bovin, filtre les vaccinations avec le nom spécifié, détermine la date de la dernière dose, puis vérifie si les conditions
     * pour l'insertion de l'injection sont remplies. Si oui, elle crée et enregistre une nouvelle entité d'injection dans le référentiel.
     *
     * Les conditions pour l'insertion incluent la disponibilité du vaccin, le fait qu'il soit actif, que la date de la dernière dose soit avant
     * aujourd'hui, et que le nombre de doses administrées n'ait pas atteint le nombre maximal de doses pour ce vaccin, ou que le carnet soit vide.
     *
     * @param idBovin L'ID du bovin pour lequel l'injection (vaccination) doit être insérée.
     * @param nom Le nom du vaccin à injecter.
     * @throws NotFoundException Si le bovin n'est pas trouvé dans le référentiel, si le vaccin n'est pas trouvé, ou si le vaccin n'est pas actif.
     */
    @Override
    public void insertInjection(Long idBovin, String nom) {

        Set<Vaccination> carnet = getCarnetVaccination(idBovin).stream()
                .filter(vaccination -> vaccination.nom().equals(nom))
                .collect(Collectors.toSet());

        LocalDate derniereDose = LocalDate.MIN;

        for (Vaccination vaccination : carnet) {
            if(vaccination.dateRappel().isAfter(derniereDose))
                derniereDose=vaccination.dateRappel();
        }

        Specification<VaccinEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),nom));

        if(vaccinRepository.findOne(specification).isPresent() &&
                vaccinRepository.findOne(specification).get().isActif()&&
                derniereDose.isBefore(LocalDate.now().plusDays(1)) &&
                ((carnet.size() < vaccinRepository.findOne(specification).get().getNbDose())|| carnet.isEmpty())){
            InjectionEntity entity= new InjectionEntity();

            entity.setVaccin(vaccinRepository.findOne(specification).get());
            entity.setBovin(bovinRepository.findById(idBovin).orElseThrow(()->new NotFoundException("Bovin not found")));
            entity.setDateInjection(LocalDate.now());

            injectionRepository.save(entity);
        }
    }

    /**
     * Récupère le carnet de vaccinations d'un bovin spécifié par son ID.
     *
     * Cette méthode prend l'ID d'un bovin en entrée, puis récupère toutes les injections associées à ce bovin dans le référentiel des injections.
     * Elle parcourt ensuite tous les vaccins disponibles dans le référentiel des vaccins, filtre les injections associées à chaque vaccin, puis
     * construit un ensemble de Vaccination représentant le carnet de vaccinations pour le bovin. Chaque élément de l'ensemble contient le nom du
     * vaccin, le nombre maximal de doses, le nombre de doses administrées, l'état d'activité du vaccin, et la date de rappel basée sur les doses
     * administrées et le délai spécifié par le vaccin.
     *
     * @param idBovin L'ID du bovin pour lequel le carnet de vaccinations doit être récupéré.
     * @return Un ensemble (Set) de Vaccination représentant le carnet de vaccinations du bovin.
     */
    @Override
    public Set<Vaccination> getCarnetVaccination(Long idBovin) {

        Set<Vaccination> carnetVaccination = new HashSet<>();

        Specification<InjectionEntity> specification = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bovin").get("id"),idBovin)));

        Set<InjectionEntity> injections = new HashSet<>(injectionRepository.findAll(specification));
        for (VaccinEntity v : vaccinRepository.findAll()) {
            Set<InjectionEntity> injectionsTriees = injections.stream()
                    .filter(injection -> injection.getVaccin()==v)
                    .collect(Collectors.toSet());

            LocalDate dateDernierVaccin = LocalDate.MIN;

            for (InjectionEntity injection : injectionsTriees) {
                if(injection.getDateInjection().isAfter(dateDernierVaccin))
                    dateDernierVaccin=injection.getDateInjection();
            }

            Vaccination vaccination;

            if(injectionsTriees.isEmpty()) {
                vaccination = new Vaccination(
                        v.getNom() + " (" + v.getDosage() + ")",
                        0,
                        v.getNbDose(),
                        null,
                        v.isActif());

            }else{
                vaccination = new Vaccination(
                        v.getNom() + " (" + v.getDosage() + ")",
                        injectionsTriees.size(),
                        v.getNbDose(),
                        dateDernierVaccin.plusDays(v.getDelai()),
                        v.isActif());
            }

            carnetVaccination.add(vaccination);
        }
        return carnetVaccination;
    }

    /**
     * Récupère un vaccin à partir de son nom.
     *
     * Cette méthode prend le nom d'un vaccin en entrée, interroge le référentiel des vaccins pour obtenir le vaccin correspondant, et le retourne
     * sous forme d'une entité VaccinEntity.
     *
     * @param nom Le nom du vaccin à récupérer.
     * @return Une entité VaccinEntity représentant le vaccin correspondant au nom spécifié.
     * @throws NotFoundException Si le vaccin n'est pas trouvé dans le référentiel.
     */
    @Override
    public VaccinEntity getVaccin(String nom) {

        Specification<VaccinEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),nom));

        return vaccinRepository.findOne(specification).orElseThrow(()->new NotFoundException("Vaccin not found"));
    }

    /**
     * Insère un nouveau vaccin dans le référentiel.
     *
     * Cette méthode prend un formulaire de vaccin en entrée, vérifie si un vaccin avec le même nom existe déjà dans le référentiel,
     * puis insère un nouveau vaccin en créant une entité VaccinEntity avec les informations fournies dans le formulaire.
     *
     * @param form Le formulaire de vaccin contenant les informations du vaccin à insérer.
     * @throws AlreadyExistsException Si un vaccin avec le même nom existe déjà dans le référentiel.
     */
    @Override
    public void insertVaccin(VaccinForm form) {

        Specification<VaccinEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),form.nom()));


        if(vaccinRepository.exists(specification))
            throw new AlreadyExistsException("Vaccin existe déjà");

        VaccinEntity entity = new VaccinEntity();

        entity.setNom(form.nom());
        entity.setDosage(form.dosage());
        entity.setNbDose(form.nbDose());
        entity.setDelai(form.delai());
        entity.setActif(true);

        vaccinRepository.save(entity);
    }

    /**
     * Met à jour les informations d'un vaccin existant dans le référentiel à partir de son ID.
     *
     * Cette méthode prend l'ID du vaccin à mettre à jour et un formulaire de vaccin en entrée. Elle vérifie si un autre vaccin avec le même nom existe
     * déjà dans le référentiel (à l'exception du vaccin à mettre à jour), puis met à jour les informations du vaccin existant en utilisant les données
     * fournies dans le formulaire.
     *
     * @param id L'ID du vaccin à mettre à jour.
     * @param form Le formulaire de vaccin contenant les nouvelles informations du vaccin.
     * @throws AlreadyExistsException Si un autre vaccin avec le même nom existe déjà dans le référentiel (à l'exception du vaccin à mettre à jour).
     * @throws NotFoundException Si le vaccin à mettre à jour n'est pas trouvé dans le référentiel.
     */
    @Override
    public void updateVaccin(Long id, VaccinForm form) {

        Specification<VaccinEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),form.nom()));


        if(vaccinRepository.exists(specification)&& vaccinRepository.findOne(specification).orElseThrow(()->new NotFoundException("Vaccin not found")).getId()!=id)
            throw new AlreadyExistsException("Vaccin déjà existant");

        VaccinEntity entity = vaccinRepository.findOne(specification).orElseThrow(()->new NotFoundException("Vaccin not found"));

        entity.setNom(form.nom());
        entity.setDosage(form.dosage());
        entity.setNbDose(form.nbDose());
        entity.setDelai(form.delai());
        entity.setActif(form.actif());

        vaccinRepository.save(entity);

    }

    /**
     * Récupère tous les vaccins disponibles dans le référentiel.
     *
     * Cette méthode interroge le référentiel des vaccins pour obtenir la liste de tous les vaccins disponibles, puis les retourne sous forme d'un ensemble
     * (Set) d'entités VaccinEntity.
     *
     * @return Un ensemble (Set) de toutes les entités VaccinEntity représentant les vaccins disponibles dans le référentiel.
     */
    @Override
    public Set<VaccinEntity> getAllVaccin() {
        return vaccinRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Identifie les bovins à vacciner avec un vaccin spécifique.
     *
     * Cette méthode prend l'ID d'un vaccin en entrée, récupère les informations sur ce vaccin à partir du référentiel, puis identifie les bovins
     * qui doivent être vaccinés avec ce vaccin en se basant sur les critères suivants :
     * - Les bovins doivent être en charge.
     * - Les bovins ne doivent pas avoir été vaccinés récemment avec ce vaccin (selon les dates de rappel).
     *
     * @param id L'ID du vaccin à utiliser pour l'identification des bovins à vacciner.
     * @return Une liste de numéros d'inscription des bovins à vacciner avec le vaccin spécifié.
     * @throws NotFoundException Si le vaccin spécifié n'est pas trouvé dans le référentiel ou si aucune vaccination correspondante n'est trouvée pour un bovin.
     */
    public List<String> toVaccinate(Long id){

        VaccinEntity vaccin = vaccinRepository.findById(id).orElseThrow(()->new NotFoundException("Vaccin not found"));

        List<String> toVaccinate = new ArrayList();

        Set<BovinEntity> bovins = bovinRepository.findAll().stream()
                .filter(BovinEntity::isEnCharge)
                .collect(Collectors.toSet());

        for (BovinEntity bovin : bovins) {

            Vaccination carnet = getCarnetVaccination(bovin.getId()).stream()
                    .filter(vaccination -> vaccination.nom().startsWith(vaccin.getNom()))
                    .findFirst().orElseThrow(()->new NotFoundException("Vacci not found"));


            if (carnet.dateRappel()==null || !carnet.dateRappel().isAfter(LocalDate.now()))
                toVaccinate.add(bovin.getNumeroInscription());

        }

        Collections.sort(toVaccinate);
        return toVaccinate;
    }

    //---------------------------------- Maladie -------------------------------------

    /**
     * Récupère toutes les maladies enregistrées dans le référentiel.
     *
     * Cette méthode interroge le référentiel des maladies pour obtenir la liste de toutes les maladies enregistrées, puis les retourne sous forme d'un ensemble
     * (Set) d'entités MaladieEntity.
     *
     * @return Un ensemble (Set) de toutes les entités MaladieEntity représentant les maladies enregistrées dans le référentiel.
     */
    @Override
    public Set<MaladieEntity> getAllMaladie() {
        return maladieRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Insère une nouvelle maladie dans le référentiel.
     *
     * Cette méthode prend le nom d'une nouvelle maladie en entrée et vérifie si une maladie portant le même nom existe déjà dans le référentiel.
     * Si aucune maladie avec le même nom n'est trouvée, la nouvelle maladie est créée et enregistrée dans le référentiel.
     *
     * @param nom Le nom de la nouvelle maladie à insérer.
     * @throws AlreadyExistsException Si une maladie avec le même nom existe déjà dans le référentiel.
     */
    @Override
    public void insertMaladie(String nom) {

        Specification<MaladieEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),nom));

        if(maladieRepository.exists(specification))
            throw new AlreadyExistsException("Maladie existe déjà");

        MaladieEntity entity = new MaladieEntity();
        entity.setNom(nom);
        maladieRepository.save(entity);
    }

    /**
     * Récupère une maladie à partir de son identifiant (ID).
     *
     * Cette méthode prend l'identifiant (ID) d'une maladie en entrée et tente de récupérer cette maladie à partir du référentiel en utilisant cet ID.
     * Si la maladie correspondante est trouvée, elle est renvoyée. Sinon, une exception de type NotFoundException est levée pour indiquer que la maladie n'a pas été trouvée.
     *
     * @param id L'identifiant (ID) de la maladie à récupérer.
     * @return L'entité MaladieEntity représentant la maladie correspondante.
     * @throws NotFoundException Si la maladie avec l'ID spécifié n'est pas trouvée dans le référentiel.
     */
    @Override
    public MaladieEntity getMaladie(Long id) {
        return maladieRepository.findById(id).orElseThrow(()->new NotFoundException("Maladie not found"));
    }

    /**
     * Met à jour le nom d'une maladie existante dans le référentiel.
     *
     * Cette méthode prend l'identifiant (ID) de la maladie à mettre à jour et le nouveau nom de la maladie en entrée.
     * Elle vérifie d'abord si une maladie avec le même nom existe déjà dans le référentiel (à l'exception de la maladie en cours de mise à jour).
     * Si une telle maladie existe, une exception de type AlreadyExistsException est levée pour indiquer que le nouveau nom est en conflit avec une autre maladie.
     * Sinon, la maladie existante est mise à jour avec le nouveau nom.
     *
     * @param id L'identifiant (ID) de la maladie à mettre à jour.
     * @param nom Le nouveau nom de la maladie.
     * @throws AlreadyExistsException Si une maladie avec le même nom (à l'exception de la maladie en cours de mise à jour) existe déjà dans le référentiel.
     * @throws NotFoundException Si la maladie avec l'ID spécifié n'est pas trouvée dans le référentiel.
     */
    @Override
    public void updateMaladie(Long id, String nom) {

        Specification<MaladieEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),nom));


        if(maladieRepository.exists(specification) && maladieRepository.findOne(specification).orElseThrow(()->new NotFoundException("Maladie not found")).getId()!=id)
            throw new AlreadyExistsException("Maladie déjà existante");

        if(!nom.isEmpty()){
            MaladieEntity entity = new MaladieEntity();
            entity.setNom(nom);
            entity.setId(id);
            maladieRepository.save(entity);
        }
    }


    //------------------------------------------ A -----------------------------------------

    /**
     * Récupère toutes les entités A associées à un bovin spécifié.
     *
     * Cette méthode prend l'identifiant (ID) d'un bovin en entrée et interroge le référentiel des entités A pour obtenir la liste de toutes les entités associées à ce bovin.
     * Elle retourne ensuite cette liste sous forme d'un ensemble (Set) d'entités AEntity.
     *
     * @param idBovin L'identifiant (ID) du bovin pour lequel les entités A sont recherchées.
     * @return Un ensemble (Set) d'entités AEntity associées au bovin spécifié.
     */
    @Override
    public Set<AEntity> getAllA(Long idBovin) {

        Specification<AEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bovin").get("id"),idBovin));

        return new HashSet<>(aRepository.findAll(specification));
    }

    /**
     * Récupère une entité A à partir de son identifiant (ID).
     *
     * Cette méthode prend l'identifiant (ID) d'une entité A en entrée et tente de récupérer cette entité à partir du référentiel en utilisant cet ID.
     * Si l'entité A correspondante est trouvée, elle est renvoyée. Sinon, une exception de type NotFoundException est levée pour indiquer que l'entité A n'a pas été trouvée.
     *
     * @param aId L'identifiant (ID) de l'entité A à récupérer.
     * @return L'entité de type A correspondante.
     * @throws NotFoundException Si l'entité A avec l'ID spécifié n'est pas trouvée dans le référentiel.
     */
    @Override
    public AEntity getOneA(Long aId) {
        return aRepository.findById(aId).orElseThrow(()->new NotFoundException("A not found"));
    }

    /**
     * Insère une nouvelle entité A associée à un bovin avec les informations fournies.
     *
     * Cette méthode prend l'identifiant (ID) d'un bovin et un formulaire (AForm) en entrée. Elle crée ensuite une nouvelle entité A avec les informations fournies dans le formulaire.
     * L'entité A nouvellement créée est associée au bovin spécifié par son ID.
     *
     * @param idBovin L'identifiant (ID) du bovin auquel l'entité A doit être associée.
     * @param form Le formulaire (AForm) contenant les informations nécessaires pour créer l'entité A.
     * @throws NotFoundException Si le bovin, le traitement ou la maladie spécifiée dans le formulaire n'est pas trouvée dans le référentiel.
     */
    @Override
    public void insertA(Long idBovin, AForm form) {
        if(form!=null){
            AEntity entity = new AEntity();
            entity.setBovins(bovinRepository.findById(idBovin).orElseThrow(()->new NotFoundException("Bovin not found")));
            if(form.traitement()==null)
                entity.setTraitement(null);
            else
                entity.setTraitement(traitementRepository.findById(form.traitement()).orElseThrow(()->new NotFoundException("Traitement not found")));
            entity.setMaladie(maladieRepository.findById(form.maladie()).orElseThrow(()->new NotFoundException("Maladie not found")));
            entity.setAnneeMaladie(form.annee());

            aRepository.save(entity);

        }

    }

    /**
     * Met à jour les informations d'une entité A avec les informations fournies.
     *
     * Cette méthode prend l'identifiant (ID) d'une entité A existante et un formulaire (AForm) en entrée. Elle recherche l'entité A correspondante dans le référentiel en utilisant l'ID spécifié, puis met à jour ses informations avec les données fournies dans le formulaire.
     *
     * @param id L'identifiant (ID) de l'entité A à mettre à jour.
     * @param form Le formulaire (AForm) contenant les nouvelles informations pour l'entité A.
     * @throws NotFoundException Si l'entité A avec l'ID spécifié n'est pas trouvée dans le référentiel, ou si le traitement ou la maladie spécifiée dans le formulaire n'est pas trouvée dans le référentiel.
     */
    @Override
    public void updateA(Long id, AForm form) {

        if(form!=null){
            AEntity entity = aRepository.findById(id).orElseThrow(()->new NotFoundException("A not found"));
            if(form.traitement()==null)
                entity.setTraitement(null);
            else
                entity.setTraitement(traitementRepository.findById(form.traitement()).orElseThrow(()->new NotFoundException("Traitement not found")));
            entity.setMaladie(maladieRepository.findById(form.maladie()).orElseThrow(()->new NotFoundException("Maladie not found")));
            entity.setAnneeMaladie(form.annee());

            aRepository.save(entity);

        }
    }

    /**
     * Supprime une entité A en fonction de son identifiant (ID).
     *
     * Cette méthode permet de supprimer une entité A du référentiel en utilisant l'ID spécifié en entrée. Si une entité correspondante est trouvée, elle sera supprimée du référentiel.
     *
     * @param id L'identifiant (ID) de l'entité A à supprimer.
     */
    @Override
    public void deleteA(Long id) {
        aRepository.deleteById(id);
    }


    //-------------------------- Traitement ---------------------------

    /**
     * Insère un nouveau traitement dans le référentiel en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode permet d'insérer un nouveau traitement dans le référentiel en utilisant les informations spécifiées dans le formulaire (TraitementForm). Avant d'insérer le traitement, elle vérifie si un traitement avec le même nom existe déjà dans le référentiel. Si c'est le cas, une AlreadyExistsException est levée.
     *
     * @param form Le formulaire (TraitementForm) contenant les informations du nouveau traitement à insérer.
     * @throws AlreadyExistsException Si un traitement avec le même nom existe déjà dans le référentiel.
     */
    @Override
    public void insertTraitement(TraitementForm form) {

        Specification<TraitementEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nomTraitement"), form.nomTraitement()));

        if(traitementRepository.exists(specification))
            throw new AlreadyExistsException("Traitement existe déjà");

        if(form!=null){
            TraitementEntity entity = new TraitementEntity();
            entity.setNomTraitement(form.nomTraitement());
            entity.setActif(form.actif());
            traitementRepository.save(entity);
        }
    }

    /**
     * Met à jour un traitement existant dans le référentiel en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode permet de mettre à jour un traitement existant dans le référentiel en utilisant les informations spécifiées dans le formulaire (TraitementForm) et l'identifiant (ID) fourni en entrée. Avant de mettre à jour le traitement, elle vérifie si un autre traitement avec le même nom existe déjà dans le référentiel (à l'exception du traitement actuel). Si c'est le cas, une AlreadyExistsException est levée.
     *
     * @param id L'identifiant (ID) du traitement à mettre à jour.
     * @param form Le formulaire (TraitementForm) contenant les nouvelles informations pour la mise à jour du traitement.
     * @throws AlreadyExistsException Si un traitement avec le même nom existe déjà dans le référentiel (à l'exception du traitement actuel).
     * @throws NotFoundException Si le traitement avec l'identifiant spécifié n'est pas trouvé dans le référentiel.
     */
    @Override
    public void updateTraitement(Long id, TraitementForm form) {

        Specification<TraitementEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nomTraitement"), form.nomTraitement()));

        if(traitementRepository.exists(specification)&& traitementRepository.findOne(specification).orElseThrow(()->new NotFoundException("Traitement not found")).getId()!=id)
            throw new AlreadyExistsException("Traitement déjà existant");

        if(form!=null){
            TraitementEntity entity = new TraitementEntity();
            entity.setNomTraitement(form.nomTraitement());
            entity.setActif(form.actif());
            entity.setId(id);
            traitementRepository.save(entity);
        }
    }

    /**
     * Récupère l'ensemble des traitements disponibles dans le référentiel.
     *
     * Cette méthode permet de récupérer tous les traitements disponibles dans le référentiel des traitements. Elle renvoie un ensemble (Set) de TraitementEntity, représentant chaque traitement enregistré dans le système.
     *
     * @return Un ensemble (Set) contenant tous les traitements disponibles dans le référentiel.
     */
    @Override
    public Set<TraitementEntity> getAllTraitement() {
        return traitementRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Récupère un traitement par son identifiant.
     *
     * Cette méthode permet de récupérer un traitement spécifique à partir de son identifiant unique. Si le traitement avec l'identifiant donné n'est pas trouvé, elle génère une exception NotFoundException.
     *
     * @param id L'identifiant unique du traitement à récupérer.
     * @return Le traitement correspondant à l'identifiant donné.
     * @throws NotFoundException Si aucun traitement correspondant à l'identifiant n'est trouvé.
     */
    @Override
    public TraitementEntity getTraitement(Long id) {
        return traitementRepository.findById(id).orElseThrow(()->new NotFoundException("Traitement not found"));
    }
}

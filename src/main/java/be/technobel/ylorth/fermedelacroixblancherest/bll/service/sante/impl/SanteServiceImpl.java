package be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.models.Vaccination;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante.SanteService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.AForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.VaccinForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.BovinRepository;
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

    //Vaccin
    @Override
    public void insertInjection(Long idBovin, String nom) {

        Set<Vaccination> carnet = getCarnetVaccination(idBovin).stream()
                .filter(vaccination -> vaccination.getNom().equals(nom))
                .collect(Collectors.toSet());

        LocalDate derniereDose = LocalDate.MIN;

        for (Vaccination vaccination : carnet) {
            if(vaccination.getDateRappel().isAfter(derniereDose))
                derniereDose=vaccination.getDateRappel();
        }

        if(vaccinRepository.findVaccinByNom(nom).isPresent() &&
                vaccinRepository.findVaccinByNom(nom).get().isActif()&&
                derniereDose.isBefore(LocalDate.now().plusDays(1)) &&
                ((carnet.size() < vaccinRepository.findVaccinByNom(nom).get().getNbDose())||carnet.size()==0)){
            InjectionEntity entity= new InjectionEntity();

            entity.setVaccin(vaccinRepository.findVaccinByNom(nom).get());
            entity.setBovin(bovinRepository.findById(idBovin).get());
            entity.setDateInjection(LocalDate.now());

            injectionRepository.save(entity);
        }
    }

    @Override
    public Set<Vaccination> getCarnetVaccination(Long idBovin) {

        Set<Vaccination> carnetVaccination = new HashSet<>();

        Set<InjectionEntity> injections = injectionRepository.findAllByBovinId(idBovin);
        for (VaccinEntity v : vaccinRepository.findAll()) {
            Set<InjectionEntity> injectionsTriees = injections.stream()
                    .filter(injection -> injection.getVaccin()==v)
                    .collect(Collectors.toSet());

            Vaccination vaccination = new Vaccination();
            vaccination.setNom(v.getNom() + " (" + v.getDosage() + ")");
            vaccination.setDoseMax(v.getNbDose());
            vaccination.setDoseAdministrees(injectionsTriees.size());

            LocalDate dateDernierVaccin = LocalDate.MIN;

            for (InjectionEntity injection : injectionsTriees) {
                if(injection.getDateInjection().isAfter(dateDernierVaccin))
                    dateDernierVaccin=injection.getDateInjection();
            }

            if(injectionsTriees.size()==0)
                vaccination.setDateRappel(null);
            else
                vaccination.setDateRappel(dateDernierVaccin.plusDays(v.getDelai()));

            carnetVaccination.add(vaccination);
        }
        return carnetVaccination;
    }

    @Override
    public VaccinEntity getVaccin(String nom) {
        return vaccinRepository.findVaccinByNom(nom).get();
    }

    @Override
    public void insertVaccin(VaccinForm form) {
        if(vaccinRepository.existsByNom(form.getNom()))
            throw new AlreadyExistsException("Vaccin existe déjà");

        VaccinEntity entity = new VaccinEntity();

        entity.setNom(form.getNom());
        entity.setDosage(form.getDosage());
        entity.setNbDose(form.getNbDose());
        entity.setDelai(form.getDelai());
        entity.setActif(true);

        vaccinRepository.save(entity);
    }

    @Override
    public void updateVaccin(Long id, VaccinForm form) {

        if(vaccinRepository.existsByNom(form.getNom())&& vaccinRepository.findVaccinByNom(form.getNom()).get().getId()!=id)
            throw new AlreadyExistsException("Vaccin déjà existant");

        VaccinEntity entity = vaccinRepository.findVaccinByNom(form.getNom()).get();

        entity.setNom(form.getNom());
        entity.setDosage(form.getDosage());
        entity.setNbDose(form.getNbDose());
        entity.setDelai(form.getDelai());
        entity.setActif(form.isActif());

        vaccinRepository.save(entity);

    }

    @Override
    public Set<VaccinEntity> getAllVaccin() {
        return vaccinRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    public List<String> toVaccinate(Long id){

        VaccinEntity vaccin = vaccinRepository.findById(id).get();

        List<String> toVaccinate = new ArrayList();

        Set<BovinEntity> bovins = bovinRepository.findAll().stream()
                .filter(BovinEntity::isEnCharge)
                .collect(Collectors.toSet());

        for (BovinEntity bovin : bovins) {

            Vaccination carnet = getCarnetVaccination(bovin.getId()).stream()
                    .filter(vaccination -> vaccination.getNom().startsWith(vaccin.getNom()))
                    .findFirst().get();


            if (carnet.getDateRappel()==null || !carnet.getDateRappel().isAfter(LocalDate.now()))
                toVaccinate.add(bovin.getNumeroInscription());

        }

        Collections.sort(toVaccinate);
        return toVaccinate;
    }

    //Maladie

    @Override
    public Set<MaladieEntity> getAllMaladie() {
        return maladieRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public void insertMaladie(String nom) {
        if(maladieRepository.existsByNom(nom))
            throw new AlreadyExistsException("Maladie existe déjà");

        MaladieEntity entity = new MaladieEntity();
        entity.setNom(nom);
        maladieRepository.save(entity);
    }
    @Override
    public MaladieEntity getMaladie(Long id) {
        return maladieRepository.findById(id).get();
    }

    @Override
    public void updateMaladie(Long id, String nom) {
        if(maladieRepository.existsByNom(nom) && maladieRepository.findByNom(nom).get().getId()!=id)
            throw new AlreadyExistsException("Maladie déjà existante");

        if(!nom.equals("")){
            MaladieEntity entity = new MaladieEntity();
            entity.setNom(nom);
            entity.setId(id);
            maladieRepository.save(entity);
        }
    }

    // A

    @Override
    public Set<AEntity> getAllA(Long idBovin) {
        return aRepository.findAllByBovinId(idBovin).stream()
                .collect(Collectors.toSet());
    }

    @Override
    public AEntity getOneA(Long aId) {
        return aRepository.findById(aId).get();
    }

    @Override
    public void insertA(Long idBovin, AForm form) {
        if(form!=null){
            AEntity entity = new AEntity();
            entity.setBovins(bovinRepository.findById(idBovin).get());
            if(form.getTraitement()==null)
                entity.setTraitement(null);
            else
                entity.setTraitement(traitementRepository.findById(form.getTraitement()).get());
            entity.setMaladie(maladieRepository.findById(form.getMaladie()).get());
            entity.setAnneeMaladie(form.getAnnee());

            aRepository.save(entity);

        }

    }

    @Override
    public void updateA(Long id, AForm form) {

        if(form!=null){
            AEntity entity = aRepository.findById(id).get();
            if(form.getTraitement()==null)
                entity.setTraitement(null);
            else
                entity.setTraitement(traitementRepository.findById(form.getTraitement()).get());
            entity.setMaladie(maladieRepository.findById(form.getMaladie()).get());
            entity.setAnneeMaladie(form.getAnnee());

            aRepository.save(entity);

        }
    }

    @Override
    public void deleteA(Long id) {
        aRepository.deleteById(id);
    }

    // Traitement

    @Override
    public void insertTraitement(TraitementForm form) {
        if(traitementRepository.existsByNomTraitement(form.getNomTraitement()))
            throw new AlreadyExistsException("Traitement existe déjà");

        if(form!=null){
            TraitementEntity entity = new TraitementEntity();
            entity.setNomTraitement(form.getNomTraitement());
            entity.setActif(form.isActif());
            traitementRepository.save(entity);
        }
    }

    @Override
    public void updateTraitement(Long id, TraitementForm form) {
        if(traitementRepository.existsByNomTraitement(form.getNomTraitement())&& traitementRepository.findByNomTraitement(form.getNomTraitement()).get().getId()!=id)
            throw new AlreadyExistsException("Traitement déjà existant");

        if(form!=null){
            TraitementEntity entity = new TraitementEntity();
            entity.setNomTraitement(form.getNomTraitement());
            entity.setActif(form.isActif());
            entity.setId(id);
            traitementRepository.save(entity);
        }
    }
    @Override
    public Set<TraitementEntity> getAllTraitement() {
        return traitementRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public TraitementEntity getTraitement(Long id) {
        return traitementRepository.findById(id).get();
    }
}

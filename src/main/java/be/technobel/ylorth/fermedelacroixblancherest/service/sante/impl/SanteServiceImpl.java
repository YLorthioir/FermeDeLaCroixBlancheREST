package be.technobel.ylorth.fermedelacroixblancherest.service.sante.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.AForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.BovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.service.sante.SanteService;
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
            Injection entity= new Injection();

            entity.setVaccin(vaccinRepository.findVaccinByNom(nom).get());
            entity.setBovin(bovinRepository.findById(idBovin).get());
            entity.setDateInjection(LocalDate.now());

            injectionRepository.save(entity);
        }
    }

    @Override
    public Set<Vaccination> getCarnetVaccination(Long idBovin) {

        Set<Vaccination> carnetVaccination = new HashSet<>();

        Set<Injection> injections = injectionRepository.findAllByBovinId(idBovin);
        for (Vaccin v : vaccinRepository.findAll()) {
            Set<Injection> injectionsTriees = injections.stream()
                    .filter(injection -> injection.getVaccin()==v)
                    .collect(Collectors.toSet());

            Vaccination vaccination = new Vaccination();
            vaccination.setNom(v.getNom() + " (" + v.getDosage() + ")");
            vaccination.setDoseMax(v.getNbDose());
            vaccination.setDoseAdministrees(injectionsTriees.size());

            LocalDate dateDernierVaccin = LocalDate.MIN;

            for (Injection injection : injectionsTriees) {
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
    public VaccinDTO getVaccin(String nom) {
        return VaccinDTO.toDTO(vaccinRepository.findVaccinByNom(nom).get());
    }

    @Override
    public void insertVaccin(VaccinForm form) {
        if(vaccinRepository.existsByNom(form.getNom()))
            throw new AlreadyExistsException("Vaccin existe déjà");

        Vaccin entity = new Vaccin();

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

        Vaccin entity = vaccinRepository.findVaccinByNom(form.getNom()).get();

        entity.setNom(form.getNom());
        entity.setDosage(form.getDosage());
        entity.setNbDose(form.getNbDose());
        entity.setDelai(form.getDelai());
        entity.setActif(form.isActif());

        vaccinRepository.save(entity);

    }

    @Override
    public Set<VaccinDTO> getAllVaccin() {
        return vaccinRepository.findAll().stream()
                .map(VaccinDTO::toDTO)
                .collect(Collectors.toSet());
    }

    public List<String> toVaccinate(Long id){

        Vaccin vaccin = vaccinRepository.findById(id).get();

        List<String> toVaccinate = new ArrayList();

        Set<Bovin> bovins = bovinRepository.findAll().stream()
                .filter(Bovin::isEnCharge)
                .collect(Collectors.toSet());

        for (Bovin bovin : bovins) {

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
    public Set<MaladieDTO> getAllMaladie() {
        return maladieRepository.findAll().stream()
                .map(MaladieDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public void insertMaladie(String nom) {
        if(maladieRepository.existsByNom(nom))
            throw new AlreadyExistsException("Maladie existe déjà");

        Maladie entity = new Maladie();
        entity.setNom(nom);
        maladieRepository.save(entity);
    }
    @Override
    public MaladieDTO getMaladie(Long id) {
        return MaladieDTO.toDTO(maladieRepository.findById(id).get());
    }

    @Override
    public void updateMaladie(Long id, String nom) {
        if(maladieRepository.existsByNom(nom) && maladieRepository.findByNom(nom).get().getId()!=id)
            throw new AlreadyExistsException("Maladie déjà existante");

        if(!nom.equals("")){
            Maladie entity = new Maladie();
            entity.setNom(nom);
            entity.setId(id);
            maladieRepository.save(entity);
        }
    }

    // A

    @Override
    public Set<ADTO> getAllA(Long idBovin) {
        return aRepository.findAllByBovinId(idBovin).stream()
                .map(ADTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ADTO getOneA(Long aId) {
        return ADTO.toDTO(aRepository.findById(aId).get());
    }

    @Override
    public void insertA(Long idBovin, AForm form) {
        if(form!=null){
            A entity = new A();
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
            A entity = aRepository.findById(id).get();
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
            Traitement entity = new Traitement();
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
            Traitement entity = new Traitement();
            entity.setNomTraitement(form.getNomTraitement());
            entity.setActif(form.isActif());
            entity.setId(id);
            traitementRepository.save(entity);
        }
    }
    @Override
    public Set<TraitementDTO> getAllTraitement() {
        return traitementRepository.findAll().stream()
                .map(TraitementDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public TraitementDTO getTraitement(Long id) {
        return TraitementDTO.toDTO(traitementRepository.findById(id).get());
    }
}

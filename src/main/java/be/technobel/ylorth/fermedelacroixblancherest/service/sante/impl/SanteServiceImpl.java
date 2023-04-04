package be.technobel.ylorth.fermedelacroixblancherest.service.sante.impl;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Injection;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Vaccin;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.BovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.ARepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.InjectionRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.VaccinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.sante.SanteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SanteServiceImpl implements SanteService {

    private final InjectionRepository injectionRepository;
    private final VaccinRepository vaccinRepository;
    private final ARepository aRepository;
    private final BovinRepository bovinRepository;

    public SanteServiceImpl(InjectionRepository injectionRepository,
                            VaccinRepository vaccinRepository,
                            ARepository aRepository,
                            BovinRepository bovinRepository) {
        this.injectionRepository = injectionRepository;
        this.vaccinRepository = vaccinRepository;
        this.aRepository = aRepository;
        this.bovinRepository = bovinRepository;
    }

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
                    .filter(injection -> injection.getVaccin()==v).collect(Collectors.toSet());

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
        if(vaccinRepository.findVaccinByNom(form.getNom()).isEmpty()){
            Vaccin entity = new Vaccin();

            entity.setNom(form.getNom());
            entity.setDosage(form.getDosage());
            entity.setNbDose(form.getNbDose());
            entity.setDelai(form.getDelai());
            entity.setActif(true);

            vaccinRepository.save(entity);
        }
    }

    @Override
    public void updateVaccin(Long id, VaccinForm form) {

        if(vaccinRepository.findVaccinByNom(form.getNom()).isPresent()){

            Vaccin entity = vaccinRepository.findVaccinByNom(form.getNom()).get();

            entity.setNom(form.getNom());
            entity.setDosage(form.getDosage());
            entity.setNbDose(form.getNbDose());
            entity.setDelai(form.getDelai());
            entity.setActif(form.isActif());

            vaccinRepository.save(entity);
        }
    }

    @Override
    public Set<VaccinDTO> getAllVaccin() {
        return vaccinRepository.findAll().stream()
                .map(VaccinDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MaladieDTO> getAllMaladie() {
        return null;
    }

    @Override
    public void insertMaladie(String nom) {

    }

    @Override
    public void insertTraitement(String nom, Long idMaladie) {

    }

    @Override
    public void updateTraitement(Long id, TraitementUpdateForm form) {

    }

    @Override
    public Set<ADTO> getAllA(Long idBovin) {
        return aRepository.findAllByBovinId(idBovin).stream()
                .map(ADTO::toDTO)
                .collect(Collectors.toSet());
    }
}

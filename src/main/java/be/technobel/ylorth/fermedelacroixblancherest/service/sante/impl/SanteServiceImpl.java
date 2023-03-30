package be.technobel.ylorth.fermedelacroixblancherest.service.sante.impl;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.A;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Injection;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Vaccin;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.ARepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.InjectionRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.sante.VaccinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.sante.SanteService;
import org.springframework.stereotype.Controller;
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

    public SanteServiceImpl(InjectionRepository injectionRepository,
                            VaccinRepository vaccinRepository,
                            ARepository aRepository) {
        this.injectionRepository = injectionRepository;
        this.vaccinRepository = vaccinRepository;
        this.aRepository = aRepository;
    }

    @Override
    public void insertInjection(Long idBovin, Long vaccin) {

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
    public VaccinDTO getVaccin(Long id) {
        return null;
    }

    @Override
    public void insertVaccin(VaccinInsertForm form) {

    }

    @Override
    public void updateVaccin(Long id, VaccinUpdateForm form) {

    }

    @Override
    public Set<VaccinDTO> getAllVaccin() {
        return null;
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
        return aRepository.findAllByBovinsId(idBovin).stream()
                .map(ADTO::toDTO)
                .collect(Collectors.toSet());
    }
}

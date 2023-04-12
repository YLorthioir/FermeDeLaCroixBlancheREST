package be.technobel.ylorth.fermedelacroixblancherest.service.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.AForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinForm;

import java.util.Set;

public interface SanteService {
    void insertInjection(Long idBovin, String nom);
    Set<Vaccination> getCarnetVaccination(Long idBovin);
    VaccinDTO getVaccin(String nom);
    void insertVaccin(VaccinForm form);
    void updateVaccin(Long id, VaccinForm form);
    Set<VaccinDTO> getAllVaccin();
    Set<MaladieDTO> getAllMaladie();
    void insertMaladie(String nom);
    MaladieDTO getMaladie(Long id);
    void updateMaladie(Long id, String nom);
    Set<ADTO> getAllA(Long idBovin);
    ADTO getOneA(Long aId);
    void insertA(Long idBovin, AForm form);
    void updateA(Long id, AForm form);
    void deleteA(Long id);
    void insertTraitement(TraitementForm form);
    void updateTraitement(Long id, TraitementForm form);
    Set<TraitementDTO> getAllTraitement();
    TraitementDTO getTraitement(Long id);


}

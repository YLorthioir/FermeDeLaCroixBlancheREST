package be.technobel.ylorth.fermedelacroixblancherest.service.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.MaladieDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.VaccinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.Vaccination;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinUpdateForm;

import java.util.Set;

public interface SanteService {
    void insertInjection(Long idBovin, Long vaccin);
    Set<Vaccination> getCarnetVaccination(Long idBovin);
    VaccinDTO getVaccin(Long id);
    void insertVaccin(VaccinInsertForm form);
    void updateVaccin(Long id, VaccinUpdateForm form);
    Set<VaccinDTO> getAllVaccin();
    Set<MaladieDTO> getAllMaladie();
    void insertMaladie(String nom);
    void insertTraitement(String nom, Long idMaladie);
    void updateTraitement(Long id, TraitementUpdateForm form);
    Set<ADTO> getAllA(Long idBovin);

}

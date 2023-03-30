package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinEngraissementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinReproductionUpdateForm;

import java.util.Set;
import java.util.function.Supplier;


public interface BovinService {
    Set<BovinDTO> getAll();
    BovinDTO getOne(String numeroInscription);
    Set<BovinDTO> getChildren(Long id);
    Set<BovinDTO> getParents(Long id);
    void createBovin(BovinInsertForm form);
    void updateBovinEngraissement(Long id, BovinEngraissementUpdateForm form);
    void updateBovinReproduction(Long id,BovinReproductionUpdateForm form);


}

package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinEngraissementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinReproductionUpdateForm;

import java.util.Set;


public interface BovinService {
    BovinDTO getOne(Long id);
    Set<BovinDTO> getChildren(Long id);
    Set<BovinDTO> getParents(Long id);
    void createBovin(BovinInsertForm form);
    void updateBovinEngraissement(Long id, BovinEngraissementUpdateForm form);
    void updateBovinReproduction(Long id,BovinReproductionUpdateForm form);


}

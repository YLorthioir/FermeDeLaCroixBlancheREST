package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.InfosEngraissement;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.InfosReproduction;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinUpdateTypeForm;

import java.util.Set;

public interface BovinService{
    Set<String> getAllNI();
    BovinDTO getOne(String numeroInscription);
    Set<BovinDTO> getChildren(String numeroInscription);
    void createBovin(BovinInsertForm form);
    void updateBovin(Long id,BovinUpdateForm form);
    InfosReproduction getInfosReproduction(Long id);
    InfosEngraissement getInfosEngraissement(Long id);
    void updateType(Long id, BovinUpdateTypeForm form);

}

package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.*;

import java.util.Set;

public interface BovinService{
    Set<String> getAllNI();
    Bovin getOne(String numeroInscription);
    Set<BovinEntity> getChildren(String numeroInscription);
    void createBovin(BovinInsertForm form);
    void updateBovin(Long id,BovinUpdateForm form);
    InfosReproduction getInfosReproduction(Long id);
    InfosEngraissement getInfosEngraissement(Long id);
    void updateType(Long id, BovinUpdateTypeForm form);
    Set<BovinEntity> getAllTaureaux();
    Set<String> getAllEngraissement();

}

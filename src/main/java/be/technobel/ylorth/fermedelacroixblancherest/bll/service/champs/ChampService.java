package be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.CultureForm;

import java.util.Set;

public interface ChampService {

    Set<ChampEntity> getAll();
    ChampEntity getChamp(Long id);
    CultureEntity getCulture(Long id);
    void insertChamp(ChampInsertForm form);
    void updateChamp(Long id, ChampUpdateForm form);
    void updateCulture(Long id, CultureForm form);
    Set<CultureEntity> getHistorique(Long id);
    void insertCulture(CultureForm form);
    void insertGrain(String nom);
    void updateGrain(Long id, String nom);
    Set<TypeDeGrainEntity> getAllGrains();
    TypeDeGrainEntity getOneGrain(Long id);

}

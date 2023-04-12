package be.technobel.ylorth.fermedelacroixblancherest.service.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.CultureDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.TypeDeGrainDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.CultureForm;

import java.util.Set;

public interface ChampService {

    Set<ChampDTO> getAll();
    ChampDTO getChamp(Long id);
    CultureDTO getCulture(Long id);
    void insertChamp(ChampInsertForm form);
    void updateChamp(Long id, ChampUpdateForm form);
    void updateCulture(Long id, CultureForm form);
    Set<CultureDTO> getHistorique(Long id);
    void insertCulture(CultureForm form);
    void insertGrain(String nom);
    void updateGrain(Long id, String nom);
    Set<TypeDeGrainDTO> getAllGrains();
    TypeDeGrainDTO getOneGrain(Long id);

}

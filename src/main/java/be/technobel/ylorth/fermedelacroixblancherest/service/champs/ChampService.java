package be.technobel.ylorth.fermedelacroixblancherest.service.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.CultureDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.CultureUpdateForm;

import java.util.Set;

public interface ChampService {

    Set<ChampDTO> getAll();
    ChampDTO getChamp(Long id);
    CultureDTO getCulture(Long id);
    void insert(ChampInsertForm form);
    void updateChamp(Long id, ChampUpdateForm form);
    void updateCulture(Long id, CultureUpdateForm form);
    void getHistorique(Long id);
    void insertGrain(String nom);

}

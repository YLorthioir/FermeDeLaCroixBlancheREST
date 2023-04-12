package be.technobel.ylorth.fermedelacroixblancherest.service.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.FaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.FaucheInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.FaucheUpdateForm;

import java.util.Set;

public interface FaucheService {
    FaucheDTO getOne(Long id);
    void insert(FaucheInsertForm form);
    void update(Long id, FaucheUpdateForm form);
    Set<FaucheDTO> getAll(String nomChamp);
    Set<FaucheDTO> getAll(int annee);
    Set<Integer> getAllAnnee();

}

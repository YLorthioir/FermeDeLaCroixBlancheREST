package be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheUpdateForm;

import java.util.Set;

public interface FaucheService {
    FaucheEntity getOne(Long id);
    void insert(FaucheInsertForm form);
    void update(Long id, FaucheUpdateForm form);
    Set<FaucheEntity> getAll(String nomChamp);
    Set<FaucheEntity> getAll(int annee);
    Set<Integer> getAllAnnee();

}

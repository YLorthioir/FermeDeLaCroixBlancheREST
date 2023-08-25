package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.MelangeForm;

import java.util.Set;

public interface MelangeService {
    Set<MelangeEntity> getAll();
    MelangeEntity getOne(Long id);
    void insert(MelangeForm form);
    void update(Long id, MelangeForm form);
}

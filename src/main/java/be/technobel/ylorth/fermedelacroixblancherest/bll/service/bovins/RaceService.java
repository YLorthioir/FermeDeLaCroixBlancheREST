package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;

import java.util.Set;

public interface RaceService {
    Set<RaceEntity> getAll();
    void insert(String nom);
    void update(Long id, String nom);
    RaceEntity getOne(Long id);
}

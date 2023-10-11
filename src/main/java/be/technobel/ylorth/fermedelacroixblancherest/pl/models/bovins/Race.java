package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;

public record Race(Long id, String nom) {

    public static Race fromBLL(RaceEntity entity) {
        if (entity == null)
            return null;

        return new Race(entity.getId(), entity.getNom());
    }
}

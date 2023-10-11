package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;

import java.time.LocalDate;

public record Champ (
    long id,
    String lieu,
    double superficie,
    LocalDate dateDerniereChaux
){
    public static Champ fromBLL(ChampEntity entity){
        if (entity==null)
            return null;

        return new Champ(
                entity.getId(),
                entity.getLieu(),
                entity.getSuperficie(),
                entity.getDateDerniereChaux()
        );
    }

}

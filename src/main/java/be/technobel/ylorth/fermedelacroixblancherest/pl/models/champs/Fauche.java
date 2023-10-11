package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;

import java.time.LocalDate;

public record Fauche (
    long id,
    int annee,
    LocalDate fauche1,
    double fauche1rendement,
    LocalDate fauche2,
    double fauche2rendement,
    LocalDate fauche3,
    double fauche3rendement,
    LocalDate fauche4,
    double fauche4rendement,
    Culture culture
    ){

    public static Fauche fromBLL (FaucheEntity entity){

        if(entity==null)
            return null;

        return new Fauche(
                entity.getId(),
                entity.getAnnee(),
                entity.getFauche1(),
                entity.getFauche1rendement(),
                entity.getFauche2(),
                entity.getFauche2rendement(),
                entity.getFauche3(),
                entity.getFauche3rendement(),
                entity.getFauche4(),
                entity.getFauche4rendement(),
                Culture.fromBLL(entity.getCulture()));
    }

}

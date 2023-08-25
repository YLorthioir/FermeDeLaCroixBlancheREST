package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class Fauche {

    private long id;
    private int annee;
    private LocalDate fauche1;
    private double fauche1rendement;
    private LocalDate fauche2;
    private double fauche2rendement;
    private LocalDate fauche3;
    private double fauche3rendement;
    private LocalDate fauche4;
    private double fauche4rendement;
    private Culture culture;


    public static Fauche fromBLL (FaucheEntity entity){

        if(entity==null)
            return null;

        return Fauche.builder()
                .id(entity.getId())
                .annee(entity.getAnnee())
                .culture(Culture.fromBLL(entity.getCulture()))
                .fauche1(entity.getFauche1())
                .fauche2(entity.getFauche2())
                .fauche3(entity.getFauche3())
                .fauche4(entity.getFauche4())
                .fauche1rendement(entity.getFauche1rendement())
                .fauche2rendement(entity.getFauche2rendement())
                .fauche3rendement(entity.getFauche3rendement())
                .fauche4rendement(entity.getFauche4rendement())
                .build();
    }

}

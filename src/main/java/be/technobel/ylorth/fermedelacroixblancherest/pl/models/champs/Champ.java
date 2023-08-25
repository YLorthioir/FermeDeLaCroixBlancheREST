package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class Champ {

    private long id;
    private String lieu;
    private double superficie;
    private LocalDate dateDerniereChaux;

    public static Champ fromBLL(ChampEntity entity){
        if (entity==null)
            return null;

        return Champ.builder()
                .id(entity.getId())
                .dateDerniereChaux(entity.getDateDerniereChaux())
                .lieu(entity.getLieu())
                .superficie(entity.getSuperficie())
                .build();
    }

}

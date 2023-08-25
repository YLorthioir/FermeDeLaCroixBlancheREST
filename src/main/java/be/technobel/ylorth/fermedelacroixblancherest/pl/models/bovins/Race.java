package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Race {

    private long id;

    private String nom;


    public static Race fromBLL(RaceEntity entity) {
        if (entity == null)
            return null;

        return Race.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .build();
    }
}

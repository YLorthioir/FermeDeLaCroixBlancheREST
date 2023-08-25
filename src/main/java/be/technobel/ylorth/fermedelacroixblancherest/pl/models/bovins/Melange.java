package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;


import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Melange {

    private long id;

    private String nomMelange;
    private String description;

    public static Melange fromBLL(MelangeEntity entity) {
        if (entity == null)
            return null;

        return Melange.builder()
                .id(entity.getId())
                .nomMelange(entity.getNomMelange())
                .description(entity.getDescription())
                .build();
    }

}

package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class TypeDeGrain {

    private long id;
    private String nomGrain;

    public static TypeDeGrain fromBLL(TypeDeGrainEntity entity){
        if(entity==null)
            return null;

        return TypeDeGrain.builder()
                .id(entity.getId())
                .nomGrain(entity.getNomGrain())
                .build();
    }
}

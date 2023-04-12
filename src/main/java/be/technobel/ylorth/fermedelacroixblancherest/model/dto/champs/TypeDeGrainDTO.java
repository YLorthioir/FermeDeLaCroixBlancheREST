package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.TypeDeGrain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
public class TypeDeGrainDTO {

    private long id;
    private String nomGrain;

    public static TypeDeGrainDTO toDTO(TypeDeGrain entity){
        if(entity==null)
            return null;

        return TypeDeGrainDTO.builder()
                .id(entity.getId())
                .nomGrain(entity.getNomGrain())
                .build();
    }
}

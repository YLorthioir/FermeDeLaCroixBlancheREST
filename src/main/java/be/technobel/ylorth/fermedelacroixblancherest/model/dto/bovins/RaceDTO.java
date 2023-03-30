package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Race;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
public class RaceDTO {

    private long id;

    private String nom;


    public static RaceDTO toDTO(Race entity) {
        if (entity == null)
            return null;

        return RaceDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .build();
    }
}

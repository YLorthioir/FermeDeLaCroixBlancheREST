package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;


import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Melange;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
public class MelangeDTO {

    private long id;

    private String nomMelange;
    private String description;

    public static MelangeDTO toDTO(Melange entity) {
        if (entity == null)
            return null;

        return MelangeDTO.builder()
                .id(entity.getId())
                .nomMelange(entity.getNomMelange())
                .description(entity.getDescription())
                .build();
    }

}

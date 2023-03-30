package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.A;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Traitement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
public class TraitementDTO {

    private long id;
    private String nomTraitement;
    private boolean actif;

    public static TraitementDTO toDTO(Traitement entity){

        if(entity==null)
            return null;

        return TraitementDTO.builder()
                .id(entity.getId())
                .nomTraitement(entity.getNomTraitement())
                .build();
    }

}

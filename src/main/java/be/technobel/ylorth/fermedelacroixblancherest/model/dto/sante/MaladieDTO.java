package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Maladie;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter @Setter
public class MaladieDTO {

    private long id;
    private String nom;

    public static MaladieDTO toDTO (Maladie entity){

        if(entity==null)
            return null;

        return MaladieDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .build();
    }

}

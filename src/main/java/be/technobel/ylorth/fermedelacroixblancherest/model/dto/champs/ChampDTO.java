package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Champ;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Culture;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
public class ChampDTO {

    private long id;
    private String lieu;
    private double superficie;
    private LocalDate dateDerniereChaux;

    public static ChampDTO toDTO (Champ entity){
        if (entity==null)
            return null;

        return ChampDTO.builder()
                .id(entity.getId())
                .dateDerniereChaux(entity.getDateDerniereChaux())
                .lieu(entity.getLieu())
                .superficie(entity.getSuperficie())
                .build();
    }

}

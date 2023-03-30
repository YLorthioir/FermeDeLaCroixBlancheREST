package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.A;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class ADTO {

    private long id;
    private LocalDate anneeMaladie;
    private BovinDTO bovinsDTO;
    private MaladieDTO maladieDTO;
    private TraitementDTO traitementDTO;

    public static ADTO toDTO (A entity){

        if(entity==null)
            return null;

        return ADTO.builder()
                .id(entity.getId())
                .anneeMaladie(entity.getAnneeMaladie())
                .bovinsDTO(BovinDTO.toDTO(entity.getBovins()))
                .maladieDTO(MaladieDTO.toDTO(entity.getMaladie()))
                .traitementDTO(TraitementDTO.toDTO(entity.getTraitement()))
                .build();
    }
}

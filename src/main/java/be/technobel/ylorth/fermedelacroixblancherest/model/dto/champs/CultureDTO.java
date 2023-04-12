package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Culture;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class CultureDTO {

    private long id;
    private boolean estTemporaire;
    private LocalDate dateMiseEnCulture;
    private LocalDate dateDeFin;
    private String analysePDF;
    private LocalDate dateEpandage;
    private double qttFumier;
    private ChampDTO champ;
    private Long faucheId;
    private TypeDeGrainDTO typeDeGrainDTO;

    public static CultureDTO toDTO (Culture entity){
        if(entity==null)
            return null;

        return CultureDTO.builder()
                .id(entity.getId())
                .analysePDF(entity.getAnalysePDF())
                .dateDeFin(entity.getDateDeFin())
                .dateEpandage(entity.getDateEpandage())
                .dateMiseEnCulture(entity.getDateMiseEnCulture())
                .estTemporaire(entity.isEstTemporaire())
                .qttFumier(entity.getQttFumier())
                .champ(ChampDTO.toDTO(entity.getChamp()))
                .typeDeGrainDTO(TypeDeGrainDTO.toDTO(entity.getTypeDeGrain()))
                .build();
    }

}

package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

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
    private Long champId;
    private Long faucheId;
    private TypeDeGrainDTO typeDeGrainDTO;

}

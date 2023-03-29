package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter @Setter
public class FemelleReproductionDTO extends BovinDTO {

    private LocalDate derniereInsemination;
    private int perteGrossesse;

}

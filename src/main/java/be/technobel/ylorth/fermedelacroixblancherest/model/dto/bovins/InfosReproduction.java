package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class InfosReproduction {

    private LocalDate derniereInsemination;
    private int perteGrossesse;
    private int nbCesarienne;

}

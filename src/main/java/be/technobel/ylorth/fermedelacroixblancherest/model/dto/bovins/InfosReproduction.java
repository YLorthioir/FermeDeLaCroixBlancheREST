package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.FemelleReproduction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class InfosReproduction {

    private LocalDate derniereInsemination;
    private int perteGrossesse;

}

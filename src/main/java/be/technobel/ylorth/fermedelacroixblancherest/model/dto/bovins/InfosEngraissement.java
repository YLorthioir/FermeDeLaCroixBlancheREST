package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class InfosEngraissement{

    private double poidsSurPattes;
    private double poidsCarcasse;
    private LocalDate dateEngraissement;
    private MelangeDTO melange;

}

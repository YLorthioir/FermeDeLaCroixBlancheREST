package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteFaucheDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter @Setter
public class FaucheDTO {

    private long id;
    private int annee;
    private LocalDate fauche1;
    private double fauche1rendement;
    private LocalDate fauche2;
    private double fauche2rendement;
    private LocalDate fauche3;
    private double fauche3rendement;
    private LocalDate fauche4;
    private double fauche4rendement;
    private long idCulture;

    private CultureDTO cultureDTO;

    private Set<VenteFaucheDTO> venteFaucheDTO = new LinkedHashSet<>();

}

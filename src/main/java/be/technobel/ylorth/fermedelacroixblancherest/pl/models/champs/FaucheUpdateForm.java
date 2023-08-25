package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
@Data
public class FaucheUpdateForm {
    private Long cultureId;
    private int annee;
    @Past
    private LocalDate fauche1;
    @Positive
    private Integer fauche1Rendement;
    @Past
    private LocalDate fauche2;
    private Integer fauche2Rendement;
    @Past
    private LocalDate fauche3;
    private Integer fauche3Rendement;
    @Past
    private LocalDate fauche4;
    private Integer fauche4Rendement;
}

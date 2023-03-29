package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class FaucheUpdateForm {
    Long champId;
    int annee;
    @Past
    LocalDate fauche1;
    @Positive
    Integer fauche1Rendement;
    @Past
    LocalDate fauche2;
    Integer fauche2Rendement;
    @Past
    LocalDate fauche3;
    Integer fauche3Rendement;
    @Past
    LocalDate fauche4;
    Integer fauche4Rendement;
}

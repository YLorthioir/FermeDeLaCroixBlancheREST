package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class FaucheInsertForm {
    Long champId;
    @Positive
    int annee;
    @Past
    LocalDate date;
    @Positive
    int rendement;
}

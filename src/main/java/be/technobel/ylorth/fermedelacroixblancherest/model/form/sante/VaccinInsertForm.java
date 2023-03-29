package be.technobel.ylorth.fermedelacroixblancherest.model.form.sante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class VaccinInsertForm {
    @NotBlank
    private String nom;
    @Positive
    private int nbDose;
    @Positive
    private int delai;
    @NotBlank
    private String dosage;
}

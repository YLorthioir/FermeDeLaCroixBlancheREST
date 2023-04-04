package be.technobel.ylorth.fermedelacroixblancherest.model.form.sante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VaccinForm {
    @NotBlank
    private String nom;
    @Positive
    private int nbDose;
    @Positive
    private int delai;
    @NotBlank
    private String dosage;
    private boolean actif;
}

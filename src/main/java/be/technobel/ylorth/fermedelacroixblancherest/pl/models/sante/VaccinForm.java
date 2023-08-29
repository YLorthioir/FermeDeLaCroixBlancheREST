package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VaccinForm {
    @NotBlank
    private String nom;
    @Positive(message = "Doit être positif")
    private int nbDose;
    @Positive(message = "Doit être positif")
    private int delai;
    @NotBlank
    private String dosage;
    private boolean actif;
}

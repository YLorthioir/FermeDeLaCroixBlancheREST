package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record VaccinForm (
    @NotBlank String nom,
    @Positive(message = "Doit être positif") int nbDose,
    @Positive(message = "Doit être positif") int delai,
    @NotBlank String dosage,
    boolean actif
    ){
}

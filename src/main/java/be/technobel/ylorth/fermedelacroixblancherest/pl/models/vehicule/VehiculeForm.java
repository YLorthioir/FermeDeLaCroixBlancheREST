package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule;

import jakarta.validation.constraints.NotBlank;

public record VehiculeForm(
        @NotBlank
        String plaque,
        String marque,
        String modele
) { }

package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.NotBlank;

public record MaladieForm (
    @NotBlank String nom
){
}

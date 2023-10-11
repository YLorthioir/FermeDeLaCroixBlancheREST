package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ChampInsertForm (
    @NotBlank String lieu,
    @Positive(message = "Doit être positif") double superficie,
    @NotBlank String uniteDeMesure){
}

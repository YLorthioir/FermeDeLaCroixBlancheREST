package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ChampUpdateForm (
    @NotBlank String lieu,
    @Positive(message = "Doit être positif") double superficie,
    @Past LocalDate dateDerniereChaux
    ){
}

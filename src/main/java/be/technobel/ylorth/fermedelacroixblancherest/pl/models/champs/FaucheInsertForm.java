package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record FaucheInsertForm (
    Long champId,
    int annee,
    @Past LocalDate fauche,
    @Positive(message = "Doit être positif") Integer faucheRendement
    ){
}

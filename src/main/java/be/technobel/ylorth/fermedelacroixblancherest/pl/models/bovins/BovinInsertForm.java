package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record BovinInsertForm (
    @NotBlank String numeroInscription,
    String sexe,
    @PastOrPresent LocalDate dateDeNaissance,
    @PositiveOrZero double poidsNaissance,
    boolean neCesarienne,
    Long raceId,
    String pereNI,
    String mereNI){
}

package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CultureForm (
    @NotNull Long idChamp,
    boolean temporaire,
    @Past LocalDate dateMiseEnCulture,
    @Past LocalDate dateDeFin,
    @Past LocalDate dateDernierEpandage,
    @Positive(message = "Doit être positif") int qttFumier,
    String referenceAnalyse,
    @NotNull Long grainId
    ){
}

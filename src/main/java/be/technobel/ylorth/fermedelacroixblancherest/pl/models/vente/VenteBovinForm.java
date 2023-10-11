package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record VenteBovinForm (
    @NotBlank String numeroIdentification,
    @Positive(message = "Doit être positif") int qtt,
    @Past LocalDate date,
    @Positive(message = "Doit être positif") int prixCoutant,
    @Positive(message = "Doit être positif") int prixRevente
    ){
}

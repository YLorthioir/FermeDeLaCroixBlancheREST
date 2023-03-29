package be.technobel.ylorth.fermedelacroixblancherest.model.form.vente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class VenteBovinForm {
    @NotBlank
    String numeroIdentification;
    @Positive
    int qtt;
    @Past
    LocalDate date;
    @Positive
    int prixCoutant;
    @Positive
    int prixRevente;
}

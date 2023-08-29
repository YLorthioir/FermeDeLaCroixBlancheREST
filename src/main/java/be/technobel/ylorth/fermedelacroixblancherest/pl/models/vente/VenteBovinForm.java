package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VenteBovinForm {
    @NotBlank
    private String numeroIdentification;
    @Positive(message = "Doit être positif")
    private int qtt;
    @Past
    private LocalDate date;
    @Positive(message = "Doit être positif")
    private int prixCoutant;
    @Positive(message = "Doit être positif")
    private int prixRevente;
}

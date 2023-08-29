package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ChampInsertForm {
    @NotBlank
    String lieu;
    @Positive(message = "Doit être positif")
    double superficie;
    @NotBlank
    String uniteDeMesure;
}

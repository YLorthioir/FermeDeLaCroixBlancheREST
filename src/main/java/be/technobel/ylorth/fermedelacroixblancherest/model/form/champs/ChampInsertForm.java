package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ChampInsertForm {
    @NotBlank
    String lieu;
    @Positive
    double superficie;
    String uniteDeMesure;
}

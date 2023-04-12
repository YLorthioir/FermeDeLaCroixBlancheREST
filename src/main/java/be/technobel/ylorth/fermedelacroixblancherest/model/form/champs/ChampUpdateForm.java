package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ChampUpdateForm {
    @NotBlank
    String lieu;
    @Positive
    double superficie;
    String uniteDeMesure;
    @Past
    LocalDate dateDerniereChaux;
}

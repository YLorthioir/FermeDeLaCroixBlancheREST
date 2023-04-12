package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FaucheInsertForm {
    private Long champId;
    private int annee;
    @Past
    private LocalDate fauche;
    @Positive
    private Integer faucheRendement;
}

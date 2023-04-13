package be.technobel.ylorth.fermedelacroixblancherest.model.form.vente;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VenteFaucheForm {
    private Long faucheId;
    @Positive
    private int qtt;
    @Past
    private LocalDate date;
    @Positive
    private int prixCoutant;
    @Positive
    private int prixRevente;
}

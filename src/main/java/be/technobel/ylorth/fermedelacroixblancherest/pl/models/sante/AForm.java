package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AForm {
    @NotNull
    private Long maladie;
    private Long traitement;
    @Min(2000)
    @Max(9999)
    private LocalDate annee;

}

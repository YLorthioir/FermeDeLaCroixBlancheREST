package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AForm (
    @NotNull Long maladie,
    Long traitement,
    @Min(2000) @Max(9999) LocalDate annee
    ){
}

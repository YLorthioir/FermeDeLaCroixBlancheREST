package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RaceForm {
    @NotBlank
    private String nom;
}

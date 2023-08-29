package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TypeDeGrainForm {
    @NotBlank
    private String nom;
}

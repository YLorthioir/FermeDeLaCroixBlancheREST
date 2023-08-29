package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaladieForm {
    @NotBlank
    private String nom;
}

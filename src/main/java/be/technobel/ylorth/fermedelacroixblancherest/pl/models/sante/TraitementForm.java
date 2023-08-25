package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TraitementForm {

    @NotBlank
    private String nomTraitement;
    private boolean actif;

}

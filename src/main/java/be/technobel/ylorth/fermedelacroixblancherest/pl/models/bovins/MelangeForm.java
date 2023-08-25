package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MelangeForm {
    @NotBlank
    private String nomMelange;
    @NotBlank
    private String Description;
}

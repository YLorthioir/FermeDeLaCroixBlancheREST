package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MelangeForm {
    @NotBlank
    private String nomMelange;
    @NotBlank
    private String Description;
}

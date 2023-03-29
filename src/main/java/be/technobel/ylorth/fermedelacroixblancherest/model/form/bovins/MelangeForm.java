package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import jakarta.validation.constraints.NotBlank;

public class MelangeForm {
    @NotBlank
    private String nomMelange;
    @NotBlank
    private String Description;
}

package be.technobel.ylorth.fermedelacroixblancherest.model.form.sante;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class TraitementUpdateForm {

    @NotBlank
    private String nomTraitement;
    private boolean actif;
    private Set<Long> maladie;

}

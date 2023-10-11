package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import jakarta.validation.constraints.NotBlank;

public record TraitementForm (
    @NotBlank String nomTraitement,
    boolean actif
){
}

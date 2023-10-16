package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import jakarta.validation.constraints.NotBlank;

public record TypeDeGrainForm (
    @NotBlank String nomGrain
){

}

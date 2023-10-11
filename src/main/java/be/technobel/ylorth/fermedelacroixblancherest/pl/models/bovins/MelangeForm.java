package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import jakarta.validation.constraints.NotBlank;

public record MelangeForm (
        @NotBlank String nomMelange,
        @NotBlank  String description
    ){

}

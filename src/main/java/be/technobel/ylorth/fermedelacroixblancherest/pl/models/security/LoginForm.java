package be.technobel.ylorth.fermedelacroixblancherest.pl.models.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginForm (
    @NotNull @Size(max = 40) String login,
    @NotNull @Size(min = 4, max = 40) String password
    ){
}

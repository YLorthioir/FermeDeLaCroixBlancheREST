package be.technobel.ylorth.fermedelacroixblancherest.pl.models.security;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserRole;

public record AuthResponse (
    String token,
    String login,
    UserRole role
    ){
}

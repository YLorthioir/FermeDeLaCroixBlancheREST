package be.technobel.ylorth.fermedelacroixblancherest.pl.models.security;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserRole;

import be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.constraints.ConfirmPassword;
import jakarta.validation.constraints.*;

@ConfirmPassword
public record RegisterForm (
    @NotNull @Size(min = 4, max = 40) String login,
    @NotNull @Size(min = 4, max = 40) String password,
    @NotNull @Size(min = 4, max = 40) String confirmPassword,
    @NotNull String role
        ){

    public UserRole getRole(){
        if("ADMIN".equals(role))
            return UserRole.ADMIN;

        if("EMPLOYE".equals(role))
            return UserRole.EMPLOYE;

        if("GERANT".equals(role))
            return UserRole.GERANT;

        return null;
    }
}

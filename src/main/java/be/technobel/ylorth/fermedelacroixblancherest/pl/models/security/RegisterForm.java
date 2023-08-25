package be.technobel.ylorth.fermedelacroixblancherest.pl.models.security;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserRole;

import be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.constraints.ConfirmPassword;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@ConfirmPassword
public class RegisterForm {
    @NotNull
    @Size(min = 4, max = 40)
    private String login;
    @NotNull
    @Size(min = 4, max = 40)
    private String password;
    @NotNull
    @Size(min = 4, max = 40)
    private String confirmPassword;
    @NotNull
    private String role;

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

package be.technobel.ylorth.fermedelacroixblancherest.pl.models.security;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserRole;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthResponse {

    private String token;
    private String login;
    private UserRole role;

}

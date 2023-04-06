package be.technobel.ylorth.fermedelacroixblancherest.model.dto;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.UserRole;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthDTO {

    private String token;
    private String login;
    private UserRole role;

}

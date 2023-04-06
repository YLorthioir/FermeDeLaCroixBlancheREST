package be.technobel.ylorth.fermedelacroixblancherest.model.form;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;


@Data
public class RegisterForm {
    @NotNull
    @Size(min = 4, max = 40)
    private String lastName;
    @NotNull
    @Size(min = 4, max = 40)
    private String firstName;
    @NotNull
    @Size(min = 4, max = 40)
    private String password;
    @NotNull
    @Size(min = 4, max = 40)
    private String confirmPassword;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 13)
    private String phone;
    @NotNull
    @Size(max = 4)
    private String number;
    @NotNull
    @Size(min = 4, max = 100)
    private String street;
    @NotNull
    @Min(1000)
    @Max(99999)
    private int postCode;
    @NotNull
    @Size(min = 4, max = 100)
    private String city;
    @NotNull
    @Size(min = 4, max = 100)
    private String country;
    @Past
    private LocalDate birthdate;
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

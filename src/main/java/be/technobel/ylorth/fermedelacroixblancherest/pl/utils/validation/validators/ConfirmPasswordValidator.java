package be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.validators;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.RegisterForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.constraints.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, RegisterForm> {

    @Override
    public boolean isValid(RegisterForm value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }


}

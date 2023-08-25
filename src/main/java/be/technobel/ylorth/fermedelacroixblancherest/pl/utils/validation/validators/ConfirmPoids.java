package be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.validators;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.BovinUpdateForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ConfirmPoids implements ConstraintValidator<be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.constraints.ConfirmPoids, BovinUpdateForm> {

    @Override
    public boolean isValid(BovinUpdateForm value, ConstraintValidatorContext context) {
        if(value.getPoidsSurPattes()!=0 && value.getPoidsCarcasse()!=0)
            return (value.getPoidsSurPattes() > value.getPoidsCarcasse() && value.getPoidsCarcasse() > value.getPoidsNaissance());
        else
            return true;
    }


}

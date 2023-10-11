package be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.validators;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.BovinUpdateForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ConfirmPoids implements ConstraintValidator<be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.constraints.ConfirmPoids, BovinUpdateForm> {

    @Override
    public boolean isValid(BovinUpdateForm value, ConstraintValidatorContext context) {
        if(value.poidsSurPattes()!=0 && value.poidsCarcasse()!=0)
            return (value.poidsSurPattes() > value.poidsCarcasse() && value.poidsCarcasse() > value.poidsNaissance());
        else
            return true;
    }


}

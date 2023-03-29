package be.technobel.ylorth.fermedelacroixblancherest.validation.validators;

import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinEngraissementUpdateForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ConfirmPoids implements ConstraintValidator<be.technobel.ylorth.fermedelacroixblancherest.validation.constraints.ConfirmPoids, BovinEngraissementUpdateForm> {

    @Override
    public boolean isValid(BovinEngraissementUpdateForm value, ConstraintValidatorContext context) {
            return (value.getPoidsSurPattes()> value.getPoidsCarcasse()&&value.getPoidsCarcasse()>value.getPoidsNaissance());
    }


}

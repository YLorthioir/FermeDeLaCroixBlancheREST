package be.technobel.ylorth.fermedelacroixblancherest.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = be.technobel.ylorth.fermedelacroixblancherest.validation.validators.ConfirmPoids.class)
public @interface ConfirmPoids {

    String message() default "Le poids doit être inférieur au poids sur pattes";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

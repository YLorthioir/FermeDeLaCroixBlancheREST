package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class CultureUpdateForm {
    @NotBlank
    String nom;
    @Positive
    double superficie;
    String uniteDeMesure;
    boolean temporaire;
    @Past
    LocalDate dateMiseEnCulture;
    @Past
    LocalDate dateDeFin;
    @Past
    LocalDate dateDernierEpandage;
    @Positive
    int qttFumier;
    String referenceAnalyse;
    Long grainId;

}

package be.technobel.ylorth.fermedelacroixblancherest.model.form.champs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CultureForm {
    @NotNull
    Long idChamp;
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
    @NotNull
    Long grainId;

}

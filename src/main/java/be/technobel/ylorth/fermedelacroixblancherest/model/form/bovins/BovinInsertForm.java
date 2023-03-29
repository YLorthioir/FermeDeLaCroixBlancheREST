package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class BovinInsertForm {
    @NotBlank
    private String numeroInscription;
    private char sexe;
    @Past
    private LocalDate dateDeNaissance;
    @Positive
    private double poidsNaissance;
    private boolean neCesarienne;
    private Long raceId;
    private Long pereId;
    private Long mereId;

}

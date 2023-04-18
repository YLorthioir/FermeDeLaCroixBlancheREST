package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BovinInsertForm {
    @NotBlank
    private String numeroInscription;
    private String sexe;
    @PastOrPresent
    private LocalDate dateDeNaissance;
    @Positive
    private double poidsNaissance;
    private boolean neCesarienne;
    private Long raceId;
    private String pereNI;
    private String mereNI;

}

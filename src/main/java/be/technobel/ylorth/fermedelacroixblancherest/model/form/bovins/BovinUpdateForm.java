package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.validation.constraints.ConfirmPoids;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
@ConfirmPoids
public class BovinUpdateForm {
    @NotBlank
    private String numeroInscription;
    private String sexe;
    private String nom;
    @PastOrPresent
    private LocalDate dateDeNaissance;
    @PositiveOrZero
    private double poidsNaissance;
    private boolean neCesarienne;
    private Long raceId;
    private String pereNI;
    private String mereNI;
    private Long champId;
    private boolean enCharge;
    private LocalDate dateAbattage;
    private String raisonAbattage;

    //Reproduction
    @PastOrPresent
    private LocalDate derniereInsemination;
    @PositiveOrZero
    private int perteGrossesse;

    //Engraissement
    private double poidsSurPattes;
    private double poidsCarcasse;
    @PastOrPresent
    private LocalDate dateEngraissement;
    private Long melangeId;

}

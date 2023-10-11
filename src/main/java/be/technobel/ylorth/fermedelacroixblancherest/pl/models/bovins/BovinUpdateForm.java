package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.pl.utils.validation.constraints.ConfirmPoids;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@ConfirmPoids
public record BovinUpdateForm (
    @NotBlank String numeroInscription,
    String sexe,
    String nom,
    @PastOrPresent LocalDate dateDeNaissance,
    @PositiveOrZero double poidsNaissance,
    boolean neCesarienne,
    Long raceId,
    String pereNI,
    String mereNI,
    Long champId,
    boolean enCharge,
    LocalDate dateAbattage,
    String raisonAbattage,

    //Reproduction
    @PastOrPresent LocalDate derniereInsemination,
    @PositiveOrZero int perteGrossesse,

    //Engraissement
    double poidsSurPattes,
    double poidsCarcasse,
    @PastOrPresent LocalDate dateEngraissement,
    Long melangeId
    ){
}

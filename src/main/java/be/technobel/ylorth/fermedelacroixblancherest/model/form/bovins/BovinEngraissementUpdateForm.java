package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.validation.constraints.ConfirmPoids;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ConfirmPoids
public class BovinEngraissementUpdateForm {
    private char sexe;
    @Past
    private LocalDate dateDeNaissance;
    @Positive
    private double poidsNaissance;
    private String nom;
    private boolean enCharge;
    private boolean neCesarienne;
    private Long raceId;
    private Long champsId;
    private Set<Long> a = new LinkedHashSet<>();
    private Long pereId;
    private Long mereId;
    private double poidsSurPattes;
    private double poidsCarcasse;
    @Past
    private LocalDate dateEngraissement;
    private Long melangeId;
}

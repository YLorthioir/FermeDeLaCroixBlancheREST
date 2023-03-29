package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class BovinReproductionUpdateForm {
    private char sexe;
    @Past
    private LocalDate dateDeNaissance;
    private double poidsNaissance;
    private String nom;
    private boolean enCharge;
    private boolean neCesarienne;
    private Long raceId;
    private Long champId;
    private Set<Long> a = new LinkedHashSet<>();
    private Long pereId;
    private Long mereId;
    @Past
    private LocalDate derniereInsemination;
    @Positive
    private int perteGrossesse;
}

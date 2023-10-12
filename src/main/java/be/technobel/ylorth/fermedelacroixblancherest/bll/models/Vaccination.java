package be.technobel.ylorth.fermedelacroixblancherest.bll.models;

import java.time.LocalDate;

public record Vaccination (
    String nom,
    int doseAdministrees,
    int doseMax,
    LocalDate dateRappel,
    boolean isActif
){
}

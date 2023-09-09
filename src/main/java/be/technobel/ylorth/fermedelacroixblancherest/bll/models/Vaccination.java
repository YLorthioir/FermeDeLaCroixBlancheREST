package be.technobel.ylorth.fermedelacroixblancherest.bll.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Vaccination {
    private String nom;
    private int doseAdministrees;
    private int doseMax;
    private LocalDate dateRappel;
    private boolean isActif;
}

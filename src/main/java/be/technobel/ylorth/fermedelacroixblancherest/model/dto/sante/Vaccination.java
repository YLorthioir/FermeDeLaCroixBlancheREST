package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Vaccination {
    private String nom;
    private int doseAdministrees;
    private int doseMax;
    private LocalDate dateRappel;
}

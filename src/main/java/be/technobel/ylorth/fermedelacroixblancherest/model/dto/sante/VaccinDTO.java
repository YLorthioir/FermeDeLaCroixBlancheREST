package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class VaccinDTO {

    private long id;
    private String nom;
    private int nbDose;
    private int delai;
    private String dosage;
    private boolean actif;

}

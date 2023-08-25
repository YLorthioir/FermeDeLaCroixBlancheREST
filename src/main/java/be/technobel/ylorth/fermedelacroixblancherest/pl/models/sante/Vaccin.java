package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.VaccinEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Vaccin {

    private long id;
    private String nom;
    private int nbDose;
    private int delai;
    private String dosage;
    private boolean actif;

    public static Vaccin fromBLL(VaccinEntity entity){

        if(entity==null)
            return null;

        return Vaccin.builder()
                .id(entity.getId())
                .actif(entity.isActif())
                .delai(entity.getDelai())
                .dosage(entity.getDosage())
                .nbDose(entity.getNbDose())
                .nom(entity.getNom())
                .build();

    }

}

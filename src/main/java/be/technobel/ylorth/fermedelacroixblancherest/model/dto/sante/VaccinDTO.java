package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Vaccin;
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

    public static VaccinDTO toDTO(Vaccin entity){

        if(entity==null)
            return null;

        return VaccinDTO.builder()
                .id(entity.getId())
                .actif(entity.isActif())
                .delai(entity.getDelai())
                .dosage(entity.getDosage())
                .nbDose(entity.getNbDose())
                .nom(entity.getNom())
                .build();

    }

}

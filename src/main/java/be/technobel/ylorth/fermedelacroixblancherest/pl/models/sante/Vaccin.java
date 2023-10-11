package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.VaccinEntity;

public record Vaccin (
    long id,
    String nom,
    int nbDose,
    int delai,
    String dosage,
    boolean actif
){
    
    public static Vaccin fromBLL(VaccinEntity entity){

        if(entity==null)
            return null;

        return new Vaccin(
                entity.getId(),
                entity.getNom(),
                entity.getNbDose(),
                entity.getDelai(),
                entity.getDosage(),
                entity.isActif());

    }

}

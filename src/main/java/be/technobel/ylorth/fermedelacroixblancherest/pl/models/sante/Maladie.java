package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.MaladieEntity;

public record Maladie (
    long id,
    String nom
    ){
    public static Maladie fromBLL(MaladieEntity entity){

        if(entity==null)
            return null;

        return new Maladie (
                entity.getId(),
                entity.getNom());
    }

}

package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.TraitementEntity;

public record Traitement (
    long id,
    String nomTraitement,
    boolean actif
){

    public static Traitement fromBLL(TraitementEntity entity){

        if(entity==null)
            return null;

        return new Traitement (
                entity.getId(),
                entity.getNomTraitement(),
                entity.isActif());
    }

}

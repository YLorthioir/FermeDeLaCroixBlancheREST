package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;

public record TypeDeGrain (
    long id,
    String nomGrain
){

    public static TypeDeGrain fromBLL(TypeDeGrainEntity entity){
        if(entity==null)
            return null;

        return new TypeDeGrain(
                entity.getId(),
                entity.getNomGrain());
    }
}

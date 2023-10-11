package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;

import java.time.LocalDate;

public record A (

    long id,
    LocalDate anneeMaladie,
    Bovin bovin,
    Maladie maladie,
    Traitement traitement
    ){
    public static A fromBLL(AEntity entity){

        if(entity==null)
            return null;

        return new A (
                entity.getId(),
                entity.getAnneeMaladie(),
                Bovin.fromBLL(entity.getBovin()),
                Maladie.fromBLL(entity.getMaladie()),
                Traitement.fromBLL(entity.getTraitement()));
    }
}

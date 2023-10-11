package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.Champ;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;

import java.time.LocalDate;

public record Bovin (Long id,
                     String numeroInscription,
                     char sexe,
                     LocalDate dateDeNaissance,
                     double poidsNaissance,
                     String nom,
                     boolean enCharge,
                     boolean neCesarienne,
                     Race race,
                     Champ champ,
                     String pereNI,
                     String mereNI,
                     int nbCesarienne,
                     LocalDate dateAbattage,
                     String raisonAbattage){

    public static Bovin fromBLL(BovinEntity entity){
        if(entity==null)
            return null;

       return new Bovin(entity.getId(),
                entity.getNumeroInscription(),
                entity.getSexe(),
                entity.getDateDeNaissance(),
                entity.getPoidsNaissance(),
                entity.getNom(),
                entity.isEnCharge(),
                entity.isNeCesarienne(),
                Race.fromBLL(entity.getRace()),
                Champ.fromBLL(entity.getChamp()),
                entity.getPereNI(),
                entity.getMereNI(),
                0,
                entity.getDateAbattage(),
                entity.getRaisonAbattage());


    }
}

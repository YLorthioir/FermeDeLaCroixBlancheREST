package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;

import java.time.LocalDate;

public record Culture (

    long id,
    boolean estTemporaire,
    LocalDate dateMiseEnCulture,
    LocalDate dateDeFin,
    String analysePDF,
    LocalDate dateEpandage,
    double qttFumier,
    Champ champ,
    Long faucheId,
    TypeDeGrain typeDeGrain

){
    public static Culture fromBLL(CultureEntity entity){
        if(entity==null)
            return null;

        return new Culture(
                entity.getId(),
                entity.isEstTemporaire(),
                entity.getDateMiseEnCulture(),
                entity.getDateDeFin(),
                entity.getAnalysePDF(),
                entity.getDateEpandage(),
                entity.getQttFumier(),
                Champ.fromBLL(entity.getChamp()),
                null,
                TypeDeGrain.fromBLL(entity.getTypeDeGrain()));
    }

}

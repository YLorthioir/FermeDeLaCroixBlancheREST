package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteBovinEntity;

import java.time.LocalDate;

public record VenteBovin (
    long id,
    double quantite,
    LocalDate dateDeVente,
    double prixCoutant,
    double prixRevente,
    String bovin
    ){

    public static VenteBovin fromBLL(VenteBovinEntity entity){

        if(entity==null)
            return null;

        return new VenteBovin (
                entity.getId(),
                entity.getQuantite(),
                entity.getDateDeVente(),
                entity.getPrixCoutant(),
                entity.getPrixRevente(),
                entity.getBovinEngraissement().getNumeroInscription());
    }

}

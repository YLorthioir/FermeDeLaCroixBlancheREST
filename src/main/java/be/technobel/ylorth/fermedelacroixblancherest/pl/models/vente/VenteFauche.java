package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.Fauche;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteFaucheEntity;

import java.time.LocalDate;

public record VenteFauche (
    long id,
    double quantite,
    LocalDate dateDeVente,
    double prixCoutant,
    double prixRevente,
    Fauche faucheDTO
    ){

    public static VenteFauche fromBLL(VenteFaucheEntity entity){

        if(entity==null)
            return null;

        return new VenteFauche(
                entity.getId(),
                entity.getQuantite(),
                entity.getDateDeVente(),
                entity.getPrixCoutant(),
                entity.getPrixRevente(),
                Fauche.fromBLL(entity.getFauche()));
    }

}

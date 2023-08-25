package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteBovinEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class VenteBovin {

    private long id;
    private double quantite;
    private LocalDate dateDeVente;
    private double prixCoutant;
    private double prixRevente;
    private String bovin;

    public static VenteBovin fromBLL(VenteBovinEntity entity){

        if(entity==null)
            return null;

        return VenteBovin.builder()
                .id(entity.getId())
                .dateDeVente(entity.getDateDeVente())
                .prixCoutant(entity.getPrixCoutant())
                .prixRevente(entity.getPrixRevente())
                .quantite(entity.getQuantite())
                .bovin(entity.getBovinEngraissement().getNumeroInscription())
                .build();
    }

}

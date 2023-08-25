package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.Fauche;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteFaucheEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class VenteFauche {

    private long id;
    private double quantite;
    private LocalDate dateDeVente;
    private double prixCoutant;
    private double prixRevente;
    private Fauche faucheDTO;

    public static VenteFauche fromBLL(VenteFaucheEntity entity){

        if(entity==null)
            return null;

        return VenteFauche.builder()
                .id(entity.getId())
                .dateDeVente(entity.getDateDeVente())
                .prixCoutant(entity.getPrixCoutant())
                .prixRevente(entity.getPrixRevente())
                .quantite(entity.getQuantite())
                .faucheDTO(Fauche.fromBLL(entity.getFauche()))
                .build();
    }

}

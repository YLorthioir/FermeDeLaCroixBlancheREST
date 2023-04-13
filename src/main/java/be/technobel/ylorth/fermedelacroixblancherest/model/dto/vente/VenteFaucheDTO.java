package be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.FaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteFauche;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class VenteFaucheDTO {

    private long id;
    private double quantite;
    private LocalDate dateDeVente;
    private double prixCoutant;
    private double prixRevente;

    private FaucheDTO faucheDTO;

    public static VenteFaucheDTO toDTO(VenteFauche entity){

        if(entity==null)
            return null;

        return VenteFaucheDTO.builder()
                .id(entity.getId())
                .dateDeVente(entity.getDateDeVente())
                .prixCoutant(entity.getPrixCoutant())
                .prixRevente(entity.getPrixRevente())
                .quantite(entity.getQuantite())
                .faucheDTO(FaucheDTO.toDTO(entity.getFauche()))
                .build();
    }

}

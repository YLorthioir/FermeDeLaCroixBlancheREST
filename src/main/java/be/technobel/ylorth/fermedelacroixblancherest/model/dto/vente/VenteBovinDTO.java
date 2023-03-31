package be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteBovin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class VenteBovinDTO {

    private long id;
    private double quantite;
    private LocalDate dateDeVente;
    private double prixCoutant;
    private double prixRevente;

    public VenteBovinDTO toDTO(VenteBovin entity){

        if(entity==null)
            return null;

        return VenteBovinDTO.builder()
                .id(entity.getId())
                .dateDeVente(entity.getDateDeVente())
                .prixCoutant(entity.getPrixCoutant())
                .prixRevente(entity.getPrixRevente())
                .quantite(entity.getQuantite())
                .build();
    }

}

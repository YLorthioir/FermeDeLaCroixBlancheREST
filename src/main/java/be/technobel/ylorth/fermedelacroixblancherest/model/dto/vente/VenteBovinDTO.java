package be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinEngraissementDTO;
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
    private BovinEngraissementDTO bovinEngraissementDTO;

}

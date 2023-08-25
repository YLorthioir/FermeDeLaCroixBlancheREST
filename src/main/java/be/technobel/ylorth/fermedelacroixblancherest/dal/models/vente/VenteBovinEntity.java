package be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEngraissementEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class VenteBovinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="vente_bovin_id", nullable = false)
    private long id;
    private double quantite;
    private LocalDate dateDeVente;
    private double prixCoutant;
    private double prixRevente;

    @OneToOne()
    @JoinColumn(name = "bovin_id")
    private BovinEngraissementEntity bovinEngraissement;

}

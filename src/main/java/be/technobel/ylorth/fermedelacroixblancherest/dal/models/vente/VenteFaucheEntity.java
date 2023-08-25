package be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class VenteFaucheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="vente_fauche_id", nullable = false)
    private long id;
    private double quantite;
    private LocalDate dateDeVente;
    private double prixCoutant;
    private double prixRevente;

    @ManyToOne
    @JoinColumn(name = "fauche_id")
    private FaucheEntity fauche;

}

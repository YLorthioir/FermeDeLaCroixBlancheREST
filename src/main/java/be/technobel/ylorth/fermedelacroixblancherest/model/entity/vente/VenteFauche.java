package be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Fauche;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class VenteFauche {

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
    private Fauche fauche;

}

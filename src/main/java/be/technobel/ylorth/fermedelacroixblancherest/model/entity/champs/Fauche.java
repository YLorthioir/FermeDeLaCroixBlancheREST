package be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteFauche;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Fauche {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="fauche_id", nullable = false)
    private long id;
    @Column(nullable = false)
    private int annee;
    private LocalDate fauche1;
    private double fauche1rendement;
    private LocalDate fauche2;
    private double fauche2rendement;
    private LocalDate fauche3;
    private double fauche3rendement;
    private LocalDate fauche4;
    private double fauche4rendement;
    private long idCulture;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "culture_id")
    private Culture culture;

    @OneToMany(mappedBy = "fauche")
    private Set<VenteFauche> venteFauche = new HashSet<>();

}

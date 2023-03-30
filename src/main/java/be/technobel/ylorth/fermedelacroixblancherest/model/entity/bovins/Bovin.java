package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Champ;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.A;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Injection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Bovin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bovin_id", nullable = false)
    private long id;
    @Column(nullable = false, length = 10, unique = true)
    private String numeroInscription;
    private char sexe;
    @Column(nullable = false)
    private LocalDate dateDeNaissance;
    @Column(nullable = false)
    private double poidsNaissance;
    private String nom;
    private boolean enCharge;
    private boolean neCesarienne;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "champ_id")
    private Champ champ;

    @OneToMany(orphanRemoval = true, mappedBy = "bovin")
    private Set<Injection> injections = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true, mappedBy = "bovins")
    private Set<A> a = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "pere_id")
    private Bovin pere;

    @ManyToOne
    @JoinColumn(name = "mere_id")
    private Bovin mere;

    @OneToMany(mappedBy = "pere")
    private Set<Bovin> enfantPere;

    @OneToMany(mappedBy = "mere")
    private Set<Bovin> enfantsMere;


}

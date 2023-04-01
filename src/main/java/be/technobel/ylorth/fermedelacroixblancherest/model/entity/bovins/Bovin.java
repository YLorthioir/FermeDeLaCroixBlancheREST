package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Bovin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bovin_id", nullable = false)
    @Getter
    private long id;
    @Getter
    @Column(nullable = false, length = 14, unique = true)
    private String numeroInscription;
    @Getter
    @Column(nullable = false)
    private char sexe;
    @Getter
    @Column(nullable = false)
    private LocalDate dateDeNaissance;
    @Getter
    @Column(nullable = false)
    private double poidsNaissance;
    @Getter @Setter
    private String nom;
    @Getter @Setter
    private boolean enCharge;
    @Getter @Setter
    private boolean neCesarienne;
    @Getter
    @Column(length = 14)
    private String pereNI;
    @Getter
    @Column(length = 14)
    private String mereNI;

    @ManyToOne
    @JoinColumn(name = "race_id")
    @Getter
    private Race race;

    @ManyToOne
    @JoinColumn(name = "champ_id")
    @Getter @Setter
    private Champ champ;

    @OneToMany(orphanRemoval = true, mappedBy = "bovin")
    private Set<Injection> injections = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true, mappedBy = "bovin")
    private Set<A> a = new LinkedHashSet<>();

    // Getter & Setter Custom

    // Getter
    public Set<Injection> getInjections() {
        return Set.copyOf(injections);
    }

    public Set<A> getA() {
        return Set.copyOf(a);
    }

    // Setter


    public void setNumeroInscription(String numeroInscription) {
        if(numeroInscription.length()==14)
            this.numeroInscription = numeroInscription;
        else
            throw new IllegalArgumentException("Numero d'inscription incorrecte");
    }

    public void setSexe(char sexe) {
        if(sexe == 'M' || sexe == 'F')
            this.sexe = sexe;
        else
            throw new IllegalArgumentException("Sexe incorrecte");
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        if(dateDeNaissance.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date de naissance incorrecte");

        this.dateDeNaissance=dateDeNaissance;
    }

    public void setPoidsNaissance(double poidsNaissance) {
        if(poidsNaissance < 0)
            throw new IllegalArgumentException("Poids naissance incorrecte");

        this.poidsNaissance = poidsNaissance;
    }

    public void setPereNI(String pereNI) {
        if(pereNI==null || pereNI.length()==14)
            this.pereNI = pereNI;
        else
            throw new IllegalArgumentException("Numero d'inscription du père incorrecte");
    }

    public void setMereNI(String mereNI) {
        if(mereNI==null || mereNI.length()==14)
            this.mereNI = mereNI;
        else
            throw new IllegalArgumentException("Numero d'inscription de la mère incorrecte");
    }

    public void setRace(Race race) {
        if(race == null)
            throw new IllegalArgumentException("La race ne peut être nulle");

        this.race = race;
    }
}

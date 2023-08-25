package be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteFaucheEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FaucheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="fauche_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false)
    @Getter
    private int annee;
    @Getter
    private LocalDate fauche1;
    @Getter
    private double fauche1rendement;
    @Getter
    private LocalDate fauche2;
    @Getter
    private double fauche2rendement;
    @Getter
    private LocalDate fauche3;
    @Getter
    private double fauche3rendement;
    @Getter
    private LocalDate fauche4;
    @Getter
    private double fauche4rendement;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "culture_id")
    @Getter
    private CultureEntity culture;

    @OneToMany(mappedBy = "fauche")
    private Set<VenteFaucheEntity> venteFauche = new HashSet<>();

    //Getters & Setters customs

    //Getters

    public Set<VenteFaucheEntity> getVenteFauche() {
        return Set.copyOf(venteFauche);
    }

    //Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setAnnee(int annee) {
        if(annee > LocalDate.now().getYear())
            throw new IllegalArgumentException("Année incorrecte");

        this.annee = annee;
    }

    public void setFauche1(LocalDate fauche1) {
        if(fauche1.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date fauche1 incorrecte");

        this.fauche1 = fauche1;
    }

    public void setFauche1rendement(double fauche1rendement) {
        if(fauche1rendement<0)
            throw new IllegalArgumentException("Rendement fauche 1 ne peut pas être négatif");

        this.fauche1rendement = fauche1rendement;
    }

    public void setFauche2(LocalDate fauche2) {
        if(fauche2!=null && fauche2.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date fauche2 incorrecte");

        this.fauche2 = fauche2;
    }

    public void setFauche2rendement(double fauche2rendement) {
        if(fauche2rendement<0)
            throw new IllegalArgumentException("Rendement fauche 2 ne peut pas être négatif");

        this.fauche2rendement = fauche2rendement;
    }

    public void setFauche3(LocalDate fauche3) {
        if(fauche3!=null && fauche3.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date fauche3 incorrecte");

        this.fauche3 = fauche3;
    }

    public void setFauche3rendement(double fauche3rendement) {
        if(fauche3rendement<0)
            throw new IllegalArgumentException("Rendement fauche 3 ne peut pas être négatif");

        this.fauche3rendement = fauche3rendement;
    }

    public void setFauche4(LocalDate fauche4) {
        if(fauche4!=null && fauche4.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date fauche4 incorrecte");

        this.fauche4 = fauche4;
    }

    public void setFauche4rendement(double fauche4rendement) {
        if(fauche4rendement<0)
            throw new IllegalArgumentException("Rendement fauche 4 ne peut pas être négatif");

        this.fauche4rendement = fauche4rendement;
    }

    public void setCulture(CultureEntity culture) {
        if(culture==null)
            throw new IllegalArgumentException("Culture incorrecte");

        this.culture = culture;
    }
}

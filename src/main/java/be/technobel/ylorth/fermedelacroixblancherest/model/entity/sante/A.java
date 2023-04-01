package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class A {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="a_id", nullable = false)
    @Getter
    private long id;
    @Getter
    private LocalDate anneeMaladie;

    @ManyToOne(optional = false)
    @JoinColumn(name="bovin_id")
    @Getter
    private Bovin bovin;

    @ManyToOne
    @JoinColumn(name = "maladie_id")
    @Getter
    private Maladie maladie;

    @ManyToOne
    @JoinColumn(name = "traitement_id")
    @Getter @Setter
    private Traitement traitement;

    //Getters & Setters customs

    // Setters

    public void setAnneeMaladie(LocalDate anneeMaladie) {
        if(anneeMaladie.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Année maladie incorrecte");

        this.anneeMaladie = anneeMaladie;
    }

    public void setBovins(Bovin bovin) {
        if(bovin == null)
            throw new IllegalArgumentException("Bovin incorrecte");
        this.bovin = bovin;
    }

    public void setMaladie(Maladie maladie) {
        if(maladie == null)
            throw new IllegalArgumentException("Maladie incorrecte");

        this.maladie = maladie;
    }
}

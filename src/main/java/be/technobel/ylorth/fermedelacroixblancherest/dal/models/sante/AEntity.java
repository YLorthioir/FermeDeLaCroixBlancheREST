package be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "A")
public class AEntity {

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
    private BovinEntity bovin;

    @ManyToOne
    @JoinColumn(name = "maladie_id")
    @Getter
    private MaladieEntity maladie;

    @ManyToOne
    @JoinColumn(name = "traitement_id")
    @Getter @Setter
    private TraitementEntity traitement;


    //Getters & Setters customs

    // Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setAnneeMaladie(LocalDate anneeMaladie) {
        if(anneeMaladie.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Année maladie incorrecte");

        this.anneeMaladie = anneeMaladie;
    }

    public void setBovins(BovinEntity bovin) {
        if(bovin == null)
            throw new IllegalArgumentException("BovinEntity incorrecte");
        this.bovin = bovin;
    }

    public void setMaladie(MaladieEntity maladie) {
        if(maladie == null)
            throw new IllegalArgumentException("Maladie incorrecte");

        this.maladie = maladie;
    }
}

package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Injection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="injection_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false)
    @Getter
    private LocalDate dateInjection;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bovin_id")
    @Getter
    private Bovin bovin;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "vaccin_id")
    @Getter
    private Vaccin vaccin;

    // Getters & Setters customs
    // Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setDateInjection(LocalDate dateInjection) {
        if(dateInjection.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date incorrecte");

        this.dateInjection = dateInjection;
    }

    public void setBovin(Bovin bovin) {
        if(bovin==null)
            throw new IllegalArgumentException("Bovin incorrecte");

        this.bovin = bovin;
    }

    public void setVaccin(Vaccin vaccin) {
        if(vaccin==null)
            throw new IllegalArgumentException("Vaccin incorrecte");

        this.vaccin = vaccin;
    }
}

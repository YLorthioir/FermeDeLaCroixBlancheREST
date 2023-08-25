package be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
public class InjectionEntity {

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
    private BovinEntity bovin;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "vaccin_id")
    @Getter
    private VaccinEntity vaccin;

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

    public void setBovin(BovinEntity bovin) {
        if(bovin==null)
            throw new IllegalArgumentException("BovinEntity incorrecte");

        this.bovin = bovin;
    }

    public void setVaccin(VaccinEntity vaccin) {
        if(vaccin==null)
            throw new IllegalArgumentException("Vaccin incorrecte");

        this.vaccin = vaccin;
    }
}

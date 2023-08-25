package be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class MelangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "melange_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nomMelange;
    @Getter @Setter
    private String description;

    @OneToMany(mappedBy = "melange", orphanRemoval = true)
    private Set<BovinEngraissementEntity> bovinEngraissements = new LinkedHashSet<>();

    //Getters & Setters customs

    //Getters

    public Set<BovinEngraissementEntity> getBovinEngraissements() {
        return Set.copyOf(bovinEngraissements);
    }

    //Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }

    public void setNomMelange(String nomMelange) {
        if(nomMelange==null || nomMelange.equals(""))
            throw new IllegalArgumentException("Le lieu du mélange est incorrecte");

        this.nomMelange = nomMelange;
    }
}

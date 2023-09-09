package be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "Race")
public class RaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "race_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nom;
    @OneToMany(mappedBy = "race")
    private Set<BovinEntity> bovins = new LinkedHashSet<>();

    //Getters & Setters customs

    //Getters

    public Set<BovinEntity> getBovins() {
        return Set.copyOf(bovins);
    }

    //Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }

    public void setNom(String nom) {
        if(nom == null || nom.equals(""))
            throw new IllegalArgumentException("Le lieu de la race est incorrecte");

        this.nom = nom;
    }
}

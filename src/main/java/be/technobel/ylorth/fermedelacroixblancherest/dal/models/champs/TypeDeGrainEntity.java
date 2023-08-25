package be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class TypeDeGrainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="grain_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nomGrain;
    @OneToMany(mappedBy = "typeDeGrain", orphanRemoval = true)
    private Set<CultureEntity> cultures = new LinkedHashSet<>();

    //Getters & Setters customs

    //Getters

    public Set<CultureEntity> getCultures() {
        return Set.copyOf(cultures);
    }

    //Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setNomGrain(String nomGrain) {
        if(nomGrain==null || nomGrain.equals(""))
            throw new IllegalArgumentException("Nom du grain incorrecte");
        this.nomGrain = nomGrain;
    }
}

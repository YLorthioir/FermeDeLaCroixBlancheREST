package be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class TypeDeGrain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="grain_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nomGrain;
    @OneToMany(mappedBy = "typeDeGrain", orphanRemoval = true)
    private Set<Culture> cultures = new LinkedHashSet<>();

    //Getters & Setters customs

    //Getters

    public Set<Culture> getCultures() {
        return Set.copyOf(cultures);
    }

    //Setters

    public void setNomGrain(String nomGrain) {
        if(nomGrain==null || nomGrain.equals(""))
            throw new IllegalArgumentException("Nom du grain incorrecte");
        this.nomGrain = nomGrain;
    }
}

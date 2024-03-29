package be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "Maladie")
public class MaladieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="maladie_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nom;

    @OneToMany(mappedBy = "maladie", orphanRemoval = true)
    private Set<AEntity> a = new LinkedHashSet<>();

    // Getters & setters customs

    // Getters

    public Set<AEntity> getA() {
        return Set.copyOf(a);
    }

    // Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setNom(String nom) {
        if(nom == null || nom.equals(""))
            throw new IllegalArgumentException("Nom invalide");

        this.nom = nom;
    }
}

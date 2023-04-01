package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Maladie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="maladie_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nom;

    @OneToMany(mappedBy = "maladie", orphanRemoval = true)
    private Set<A> a = new LinkedHashSet<>();

    // Getters & setters customs

    // Getters

    public Set<A> getA() {
        return Set.copyOf(a);
    }

    // Setters

    public void setNom(String nom) {
        if(nom == null || nom.equals(""))
            throw new IllegalArgumentException("Nom invalide");

        this.nom = nom;
    }
}

package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Traitement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="traitement_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String nomTraitement;
    @Column(nullable = false)
    @Getter @Setter
    private boolean actif;

    @OneToMany(mappedBy = "traitement")
    private Set<A> a = new LinkedHashSet<>();

    // Getters & setters customs

    // Getters

    public Set<A> getA() {
        return Set.copyOf(a);
    }

    // Setters

    public void setNomTraitement(String nomTraitement) {
        if(nomTraitement==null || nomTraitement.equals(""))
            throw new IllegalArgumentException("Nom invalide");

        this.nomTraitement = nomTraitement;
    }
}

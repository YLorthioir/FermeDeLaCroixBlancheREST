package be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class TraitementEntity {

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
    public void setNomTraitement(String nomTraitement) {
        if(nomTraitement==null || nomTraitement.equals(""))
            throw new IllegalArgumentException("Nom invalide");

        this.nomTraitement = nomTraitement;
    }
}

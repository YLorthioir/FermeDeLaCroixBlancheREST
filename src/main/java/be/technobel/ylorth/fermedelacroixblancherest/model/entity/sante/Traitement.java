package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Traitement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="traitement_id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String nomTraitement;
    @Column(nullable = false)
    private boolean actif;

    @OneToMany(mappedBy = "traitement")
    private Set<A> a = new LinkedHashSet<>();

}

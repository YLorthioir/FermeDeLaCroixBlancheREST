package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Maladie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="maladie_id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "maladie", orphanRemoval = true)
    private Set<A> a = new LinkedHashSet<>();

}

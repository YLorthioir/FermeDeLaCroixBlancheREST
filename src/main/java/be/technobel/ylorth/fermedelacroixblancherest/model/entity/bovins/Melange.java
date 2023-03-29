package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Melange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "melange_id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String nomMelange;
    private String description;

    @OneToMany(mappedBy = "melange", orphanRemoval = true)
    private Set<BovinEngraissement> bovinEngraissements = new LinkedHashSet<>();

}

package be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
public class TypeDeGrain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="grain_id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String nomGrain;

    @OneToMany(mappedBy = "typeDeGrain", orphanRemoval = true)
    private Set<Culture> cultures = new LinkedHashSet<>();

}

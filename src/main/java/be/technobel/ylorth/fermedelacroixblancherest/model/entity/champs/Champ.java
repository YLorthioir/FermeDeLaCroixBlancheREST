package be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Champ {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "champ_id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String lieu;
    private double superficie;
    private LocalDate dateDerniereChaux;

    @OneToMany(mappedBy = "champ")
    private Set<Bovin> bovins = new LinkedHashSet<>();

    @OneToMany(mappedBy = "champ", orphanRemoval = true)
    private Set<Culture> cultures = new LinkedHashSet<>();

}

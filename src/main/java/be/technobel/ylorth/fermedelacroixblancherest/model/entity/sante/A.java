package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class A {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="a_id", nullable = false)
    private long id;
    private LocalDate anneeMaladie;

    @ManyToOne(optional = false)
    @JoinColumn(name="bovin_id")
    private Bovin bovins;

    @ManyToOne
    @JoinColumn(name = "maladie_id")
    private Maladie maladie;

    @ManyToOne
    @JoinColumn(name = "traitement_id")
    private Traitement traitement;

}

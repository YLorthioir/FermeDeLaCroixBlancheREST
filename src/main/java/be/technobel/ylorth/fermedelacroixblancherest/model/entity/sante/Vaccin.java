package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Vaccin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="vaccin_id", nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String nom;
    private int nbDose;
    private int delai;
    private String dosage;
    @Column(nullable = false)
    private boolean actif;

}

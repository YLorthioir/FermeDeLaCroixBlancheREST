package be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Culture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "culture_id", nullable = false)
    private long id;
    private boolean estTemporaire;
    private LocalDate dateMiseEnCulture;
    private LocalDate dateDeFin;
    private String analysePDF;
    private LocalDate dateEpandage;
    private double qttFumier;

    @ManyToOne
    @JoinColumn(name = "champ_id")
    private Champ champ;

    @OneToOne(mappedBy = "culture", orphanRemoval = true)
    private Fauche fauche;

    @ManyToOne
    @JoinColumn(name = "type_de_grain_id")
    private TypeDeGrain typeDeGrain;

}

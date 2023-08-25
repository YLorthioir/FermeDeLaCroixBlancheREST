package be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class CultureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "culture_id", nullable = false)
    @Getter
    private long id;
    @Getter @Setter
    private boolean estTemporaire;
    @Getter
    private LocalDate dateMiseEnCulture;
    @Getter
    private LocalDate dateDeFin;
    @Getter @Setter
    private String analysePDF;
    @Getter
    private LocalDate dateEpandage;
    @Getter
    private double qttFumier;

    @ManyToOne
    @JoinColumn(name = "champ_id")
    @Getter
    private ChampEntity champ;

    @OneToOne(mappedBy = "culture", orphanRemoval = true)
    @Getter @Setter
    private FaucheEntity fauche;

    @ManyToOne
    @JoinColumn(name = "type_de_grain_id")
    @Getter
    private TypeDeGrainEntity typeDeGrain;

    //Getters & Setters customs

    // Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setDateMiseEnCulture(LocalDate dateMiseEnCulture) {
        if(dateMiseEnCulture.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date de mise en culture incorrecte");

        this.dateMiseEnCulture = dateMiseEnCulture;
    }

    public void setDateDeFin(LocalDate dateDeFin) {
        if(dateDeFin!= null && dateDeFin.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date de fin incorrecte");

        this.dateDeFin = dateDeFin;
    }

    public void setDateEpandage(LocalDate dateEpandage) {
        if(dateEpandage!=null && dateEpandage.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date d'épandage incorrecte");

        this.dateEpandage = dateEpandage;
    }

    public void setQttFumier(double qttFumier) {
        if(qttFumier<0)
            throw new IllegalArgumentException("Quantité de fumier ne peut être négative");
        this.qttFumier = qttFumier;
    }

    public void setChamp(ChampEntity champ) {
        if(champ==null)
            throw new IllegalArgumentException("Champ incorrecte");
        this.champ = champ;
    }

    public void setTypeDeGrain(TypeDeGrainEntity typeDeGrain) {
        if(typeDeGrain==null)
            throw new IllegalArgumentException("Type de grain incorrecte");
        this.typeDeGrain = typeDeGrain;
    }
}

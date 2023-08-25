package be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.InjectionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BovinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bovin_id", nullable = false)
    @Getter
    private long id;
    @Getter
    @Column(nullable = false, length = 14, unique = true)
    private String numeroInscription;
    @Getter
    @Column(nullable = false)
    private char sexe;
    @Getter
    private LocalDate dateDeNaissance;
    @Getter
    @Column(nullable = true)
    private double poidsNaissance;
    @Getter @Setter
    private String nom;
    @Getter @Setter
    private boolean enCharge;
    @Getter @Setter
    @Column(nullable = true)
    private boolean neCesarienne;
    @Getter
    @Column(length = 14)
    private String pereNI;
    @Getter
    @Column(length = 14)
    private String mereNI;
    @Getter
    private LocalDate dateAbattage;
    @Getter
    private String raisonAbattage;

    @ManyToOne
    @JoinColumn(name = "race_id")
    @Getter
    private RaceEntity race;

    @ManyToOne
    @JoinColumn(name = "champ_id")
    @Getter @Setter
    private ChampEntity champ;

    @OneToMany(orphanRemoval = true, mappedBy = "bovin")
    private Set<InjectionEntity> injections = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true, mappedBy = "bovin")
    private Set<AEntity> a = new LinkedHashSet<>();

    // Getter & Setter Custom

    // Getter
    public Set<InjectionEntity> getInjections() {
        return Set.copyOf(injections);
    }

    public Set<AEntity> getA() {
        return Set.copyOf(a);
    }

    // Setter

    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }

    public void setNumeroInscription(String numeroInscription) {
        if(numeroInscription == null || numeroInscription.length()<10)
            throw new IllegalArgumentException("Numero d'inscription incorrecte");

        this.numeroInscription = numeroInscription;
    }

    public void setSexe(char sexe) {
        if(sexe == 'M' || sexe == 'F')
            this.sexe = sexe;
        else
            throw new IllegalArgumentException("Sexe incorrecte");
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        if(dateDeNaissance.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date de naissance incorrecte");

        this.dateDeNaissance=dateDeNaissance;
    }

    public void setPoidsNaissance(double poidsNaissance) {
        if(poidsNaissance < 0)
            throw new IllegalArgumentException("Poids naissance incorrecte");

        this.poidsNaissance = poidsNaissance;
    }

    public void setPereNI(String pereNI) {
        if(pereNI !=null && pereNI.length()<10)
            throw new IllegalArgumentException("Numero d'inscription du père incorrecte");

        this.pereNI = pereNI;
    }

    public void setMereNI(String mereNI) {
        if(mereNI !=null && mereNI.length()!=0 && mereNI.length()<10)
            throw new IllegalArgumentException("Numero d'inscription de la mère incorrecte");

        this.mereNI = mereNI;
    }

    public void setRace(RaceEntity race) {
        if(race == null)
            throw new IllegalArgumentException("La race ne peut être nulle");

        this.race = race;
    }

    public void setDateAbattage(LocalDate dateAbattage) {
        if(dateAbattage!= null && dateAbattage.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date d'abattage incorrecte");

        this.dateAbattage = dateAbattage;
    }

    public void setRaisonAbattage(String raisonAbattage) {
        this.raisonAbattage = raisonAbattage;
    }

// Constructeurs

    public BovinEntity(){
    }

    public BovinEntity(String numeroInscription, char sexe, LocalDate dateDeNaissance, double poidsNaissance, boolean enCharge, boolean neCesarienne, String pereNI, String mereNI, RaceEntity race) {
        this.setNumeroInscription(numeroInscription);
        this.setSexe(sexe);
        this.setDateDeNaissance(dateDeNaissance);
        this.setPoidsNaissance(poidsNaissance);
        this.setEnCharge(enCharge);
        this.setNeCesarienne(neCesarienne);
        this.setPereNI(pereNI);
        this.setMereNI(mereNI);
        this.setRace(race);
    }

    public BovinEntity(String numeroInscription, RaceEntity race, String nom){
        this.setNumeroInscription(numeroInscription);
        this.setRace(race);
        this.setNom(nom);
        this.setSexe('M');
    }
}

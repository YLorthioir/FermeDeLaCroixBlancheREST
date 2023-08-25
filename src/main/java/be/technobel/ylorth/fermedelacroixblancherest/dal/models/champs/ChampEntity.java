package be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class ChampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "champ_id", nullable = false)
    @Getter
    private long id;
    @Column(nullable = false, unique = true)
    @Getter
    private String lieu;
    @Getter
    private double superficie;
    @Getter
    private LocalDate dateDerniereChaux;

    @OneToMany(mappedBy = "champ")
    private Set<BovinEntity> bovins = new LinkedHashSet<>();

    @OneToMany(mappedBy = "champ", orphanRemoval = true)
    private Set<CultureEntity> cultures = new LinkedHashSet<>();

    //Getters & Setters customs

    //Getters

    public Set<BovinEntity> getBovins() {
        return Set.copyOf(bovins);
    }

    public Set<CultureEntity> getCultures() {
        return Set.copyOf(cultures);
    }

    //Setters

    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setLieu(String lieu) {
        if(lieu == null || lieu.equals(""))
            throw new IllegalArgumentException("Le lieu du lieu est incorrecte");

        this.lieu = lieu;
    }

    public void setSuperficie(double superficie) {
        if(superficie<=0)
            throw new IllegalArgumentException("La superficie ne peut pas être négative");
        this.superficie = superficie;
    }

    public void setDateDerniereChaux(LocalDate dateDerniereChaux) {
        if(dateDerniereChaux!= null && dateDerniereChaux.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La date de dernère chaux est incorrecte");
        this.dateDerniereChaux = dateDerniereChaux;
    }
}

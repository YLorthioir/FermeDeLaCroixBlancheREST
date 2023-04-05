package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class FemelleReproduction extends Bovin{

    @Getter
    private LocalDate derniereInsemination;
    @Getter
    private int perteGrossesse;


    // Setters custom


    public void setDerniereInsemination(LocalDate derniereInsemination) {
        if(derniereInsemination!=null && derniereInsemination.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date de dernière insémination incorrecte");

        this.derniereInsemination = derniereInsemination;
    }

    public void setPerteGrossesse(int perteGrossesse) {
        if(perteGrossesse<0)
            throw new IllegalArgumentException("Nombre de grossesses perdues ne peut être négatif");

        this.perteGrossesse = perteGrossesse;
    }

    //Constructeurs

    public FemelleReproduction(){}

    public FemelleReproduction(String numeroInscription, char sexe, LocalDate dateDeNaissance, double poidsNaissance, boolean enCharge, boolean neCesarienne, String pereNI, String mereNI, Race race, LocalDate derniereInsemination, int perteGrossesse) {
        super(numeroInscription, sexe, dateDeNaissance, poidsNaissance, enCharge, neCesarienne, pereNI, mereNI, race);
        setDerniereInsemination(derniereInsemination);
        setPerteGrossesse(perteGrossesse);
    }
}

package be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteBovinEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "BovinEngraissement")
@PrimaryKeyJoinColumn(name = "id")
public class BovinEngraissementEntity extends BovinEntity {
    @Getter
    private double poidsSurPattes;
    @Getter
    private double poidsCarcasse;
    @Getter
    private LocalDate dateEngraissement;

    @ManyToOne
    @JoinColumn(name = "melange_id")
    @Getter @Setter
    private MelangeEntity melange;

    @OneToOne(mappedBy = "bovinEngraissement", orphanRemoval = true)
    @Getter @Setter
    private VenteBovinEntity venteBovin;

    // Setter Custom

    public void setPoidsSurPattes(double poidsSurPattes) {
        if(poidsSurPattes<=0)
            throw new IllegalArgumentException("Le poids sur pattes ne peux pas être négatif");

        this.poidsSurPattes = poidsSurPattes;
    }

    public void setPoidsCarcasse(double poidsCarcasse) {
        if(poidsCarcasse<=0)
            throw new IllegalArgumentException("Le poids de la carcasse ne peux pas être négatif");
        if(poidsCarcasse>=poidsSurPattes)
            throw new IllegalArgumentException("Le poids de la carcasse ne peux excéder le poids sur pattes");

        this.poidsCarcasse = poidsCarcasse;
    }

    public void setDateEngraissement(LocalDate dateEngraissement) {
        if(dateEngraissement!= null && dateEngraissement.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date d'engraissement incorrecte");

        this.dateEngraissement = dateEngraissement;
    }

    //Constructeurs

    public BovinEngraissementEntity(){}

    public BovinEngraissementEntity(String numeroInscription, char sexe, LocalDate dateDeNaissance, double poidsNaissance, boolean enCharge, boolean neCesarienne, String pereNI, String mereNI, RaceEntity race, double poidsSurPattes, double poidsCarcasse, LocalDate dateEngraissement, MelangeEntity melange, VenteBovinEntity venteBovin) {
        super(numeroInscription, sexe, dateDeNaissance, poidsNaissance, enCharge, neCesarienne, pereNI, mereNI, race);
        setPoidsSurPattes(poidsSurPattes);
        setPoidsCarcasse(poidsCarcasse);
        setDateEngraissement(dateEngraissement);
        setMelange(melange);
        setVenteBovin(venteBovin);
    }
}

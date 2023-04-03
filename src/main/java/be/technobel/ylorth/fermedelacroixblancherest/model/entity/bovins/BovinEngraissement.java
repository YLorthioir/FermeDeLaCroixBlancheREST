package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteBovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class BovinEngraissement extends Bovin{
    @Getter
    private double poidsSurPattes;
    @Getter
    private double poidsCarcasse;
    @Getter
    private LocalDate dateEngraissement;

    @ManyToOne
    @JoinColumn(name = "melange_id")
    @Getter @Setter
    private Melange melange;

    @OneToOne(mappedBy = "bovinEngraissement", orphanRemoval = true)
    @Getter @Setter
    private VenteBovin venteBovin;

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
}

package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteBovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@PrimaryKeyJoinColumn(name = "id")
public class BovinEngraissement extends Bovin{

    private double poidsSurPattes;
    private double poidsCarcasse;
    private LocalDate dateEngraissement;

    @ManyToOne
    @JoinColumn(name = "melange_id")
    private Melange melange;

    @OneToOne(mappedBy = "bovinEngraissement", orphanRemoval = true)
    private VenteBovin venteBovin;

}

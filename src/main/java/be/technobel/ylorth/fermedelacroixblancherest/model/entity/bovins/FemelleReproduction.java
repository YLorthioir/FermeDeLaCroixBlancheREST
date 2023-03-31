package be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@PrimaryKeyJoinColumn(name = "id")
public class FemelleReproduction extends Bovin{

    private LocalDate derniereInsemination;
    private int perteGrossesse;


}

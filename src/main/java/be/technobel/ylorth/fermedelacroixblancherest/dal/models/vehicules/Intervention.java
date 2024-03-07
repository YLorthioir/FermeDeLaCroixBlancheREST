package be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Intervention {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private int heuresDeTravail;
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
}

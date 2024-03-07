package be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Vehicule {
    @Id
    private String plaque;
    private String marque;
    private String modele;
    @OneToMany(mappedBy = "vehicule")
    private List<Intervention> interventions;
}

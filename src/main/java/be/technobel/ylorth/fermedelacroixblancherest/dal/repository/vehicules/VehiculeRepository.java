package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vehicules;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule,String>{
}

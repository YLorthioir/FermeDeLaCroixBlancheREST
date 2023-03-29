package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BovinRepository extends JpaRepository<Bovin,Long> {

}
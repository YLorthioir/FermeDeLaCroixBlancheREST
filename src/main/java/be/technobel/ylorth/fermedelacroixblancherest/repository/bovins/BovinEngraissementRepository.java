package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.BovinEngraissement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BovinEngraissementRepository extends JpaRepository<BovinEngraissement,Long> {

}
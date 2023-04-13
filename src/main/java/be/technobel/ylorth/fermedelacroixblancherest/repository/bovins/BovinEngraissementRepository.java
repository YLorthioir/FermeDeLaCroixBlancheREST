package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.BovinEngraissement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BovinEngraissementRepository extends JpaRepository<BovinEngraissement,Long> {
    Optional<BovinEngraissement> findByNumeroInscription(String numeroInscription);
}
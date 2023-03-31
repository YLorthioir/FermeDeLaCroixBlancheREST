package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.FemelleReproduction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BovinRepository extends JpaRepository<Bovin,Long> {

    Optional<Bovin> findByNumeroInscription(String numeroInscription);
}
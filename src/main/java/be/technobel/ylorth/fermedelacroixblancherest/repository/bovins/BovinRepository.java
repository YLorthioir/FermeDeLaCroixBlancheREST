package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BovinRepository<B extends Bovin, L extends Number> extends JpaRepository<B,Long> {

    Optional<B> findByNumeroInscription(String numeroInscription);
}
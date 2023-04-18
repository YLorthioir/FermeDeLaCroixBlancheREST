package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraitementRepository extends JpaRepository<Traitement,Long> {
    boolean existsByNomTraitement(String nom);
    Optional<Traitement> findByNomTraitement(String nom);
}
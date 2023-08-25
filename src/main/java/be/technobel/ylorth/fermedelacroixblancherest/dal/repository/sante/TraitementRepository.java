package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.TraitementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraitementRepository extends JpaRepository<TraitementEntity,Long> {
    boolean existsByNomTraitement(String nom);
    Optional<TraitementEntity> findByNomTraitement(String nom);
}
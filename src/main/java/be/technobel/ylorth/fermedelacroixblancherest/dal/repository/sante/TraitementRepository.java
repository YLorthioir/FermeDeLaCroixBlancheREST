package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.TraitementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TraitementRepository extends JpaRepository<TraitementEntity,Long>, JpaSpecificationExecutor<TraitementEntity> {
/*    boolean existsByNomTraitement(String nom);
    Optional<TraitementEntity> findByNomTraitement(String nom);*/
}
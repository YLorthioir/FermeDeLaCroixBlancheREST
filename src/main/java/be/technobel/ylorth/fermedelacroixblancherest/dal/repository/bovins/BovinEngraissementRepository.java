package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEngraissementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BovinEngraissementRepository extends JpaRepository<BovinEngraissementEntity,Long> {
    Optional<BovinEngraissementEntity> findByNumeroInscription(String numeroInscription);
}
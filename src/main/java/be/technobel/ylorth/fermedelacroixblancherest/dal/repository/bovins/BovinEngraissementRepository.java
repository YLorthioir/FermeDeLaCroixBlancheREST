package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEngraissementEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BovinEngraissementRepository extends JpaRepository<BovinEngraissementEntity,Long>, JpaSpecificationExecutor<BovinEngraissementEntity> {
    //Optional<BovinEngraissementEntity> findByNumeroInscription(String numeroInscription);
}
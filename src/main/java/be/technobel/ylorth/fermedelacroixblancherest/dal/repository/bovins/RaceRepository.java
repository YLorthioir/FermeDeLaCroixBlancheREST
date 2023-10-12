package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RaceRepository extends JpaRepository<RaceEntity,Long>, JpaSpecificationExecutor<RaceEntity> {
/*    boolean existsByNom(String nom);
    Optional<RaceEntity> findByNom(String nom);*/
}
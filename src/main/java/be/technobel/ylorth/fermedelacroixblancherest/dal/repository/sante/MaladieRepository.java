package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.MaladieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MaladieRepository extends JpaRepository<MaladieEntity,Long>, JpaSpecificationExecutor<MaladieEntity> {
/*    boolean existsByNom(String nom);
    Optional<MaladieEntity> findByNom(String nom);*/
}
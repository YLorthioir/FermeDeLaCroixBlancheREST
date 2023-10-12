package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface ARepository extends JpaRepository<AEntity,Long>, JpaSpecificationExecutor<AEntity> {
    //Set<AEntity> findAllByBovinId(Long id);
}
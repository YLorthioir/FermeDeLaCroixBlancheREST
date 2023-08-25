package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ARepository extends JpaRepository<AEntity,Long> {
    Set<AEntity> findAllByBovinId(Long id);
}
package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.InjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface InjectionRepository extends JpaRepository<InjectionEntity,Long> {

    Set<InjectionEntity> findAllByBovinId(Long id);

}
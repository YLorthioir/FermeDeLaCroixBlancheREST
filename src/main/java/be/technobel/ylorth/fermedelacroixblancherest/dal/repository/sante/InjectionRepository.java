package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.InjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface InjectionRepository extends JpaRepository<InjectionEntity,Long>, JpaSpecificationExecutor<InjectionEntity> {

    //Set<InjectionEntity> findAllByBovinId(Long id);

}
package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vehicules;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InterventionRepository extends JpaRepository<Intervention,Long>, JpaSpecificationExecutor<Intervention> {
}

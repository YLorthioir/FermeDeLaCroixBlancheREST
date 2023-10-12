package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.VaccinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VaccinRepository extends JpaRepository<VaccinEntity,Long>, JpaSpecificationExecutor<VaccinEntity> {

/*    Optional<VaccinEntity> findVaccinByNom(String nom);

    boolean existsByNom(String nom);*/
}
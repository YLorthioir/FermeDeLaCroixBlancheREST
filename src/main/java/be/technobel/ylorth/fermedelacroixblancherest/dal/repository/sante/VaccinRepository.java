package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.VaccinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaccinRepository extends JpaRepository<VaccinEntity,Long> {

    Optional<VaccinEntity> findVaccinByNom(String nom);

    boolean existsByNom(String nom);
}
package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Vaccin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaccinRepository extends JpaRepository<Vaccin,Long> {

    Optional<Vaccin> findVaccinByNom(String nom);

    boolean existsByNom(String nom);
}
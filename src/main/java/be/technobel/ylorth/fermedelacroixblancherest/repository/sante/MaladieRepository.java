package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaladieRepository extends JpaRepository<Maladie,Long> {
    boolean existsByNom(String nom);
    Optional<Maladie> findByNom(String nom);
}
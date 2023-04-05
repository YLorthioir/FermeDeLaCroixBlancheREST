package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Maladie;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaladieRepository extends JpaRepository<Maladie,Long> {
    boolean existsByNom(String nom);
}
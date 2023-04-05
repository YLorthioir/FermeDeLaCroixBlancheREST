package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race,Long> {
    boolean existsByNom(String nom);
}
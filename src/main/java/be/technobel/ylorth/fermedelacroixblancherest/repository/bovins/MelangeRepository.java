package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Melange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MelangeRepository extends JpaRepository<Melange,Long> {
    boolean existsMelangeByNomMelange(String nom);
    Optional<Melange> findByNomMelange(String nom);
}
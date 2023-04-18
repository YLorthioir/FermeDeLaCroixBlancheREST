package be.technobel.ylorth.fermedelacroixblancherest.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Champ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampRepository extends JpaRepository<Champ,Long> {
    boolean existsByLieu(String lieu);
}
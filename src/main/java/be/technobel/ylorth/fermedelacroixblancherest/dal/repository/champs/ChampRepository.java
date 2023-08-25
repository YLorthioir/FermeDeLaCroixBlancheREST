package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampRepository extends JpaRepository<ChampEntity,Long> {
    boolean existsByLieu(String lieu);
}
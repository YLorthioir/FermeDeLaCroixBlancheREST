package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CultureRepository extends JpaRepository<CultureEntity,Long> {
    @Query("select c from Culture c where c.champ.id=?1")
    Set<CultureEntity> findAllByChamp(Long id);
}
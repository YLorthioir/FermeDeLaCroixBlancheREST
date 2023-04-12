package be.technobel.ylorth.fermedelacroixblancherest.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Culture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface CultureRepository extends JpaRepository<Culture,Long> {
    @Query("select c from Culture c where c.champ.id=?1")
    Set<Culture> findAllByChamp(Long id);
}
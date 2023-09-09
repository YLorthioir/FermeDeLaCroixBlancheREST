package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FaucheRepository extends JpaRepository<FaucheEntity,Long> {


    boolean existsByAnneeAndCultureId(int annee, Long culture);

    @Query("select f from Fauche f where (f.annee=?1 and f.culture.id=?2)")
    FaucheEntity findByAnneeAndCulture(int annee, Long idCulture);

    @Query("select f from Fauche f where f.annee=?1")
    Set<FaucheEntity> findAllByAnnee(int annee);

    @Query("select f from Fauche f where f.culture.champ.lieu=?1")
    Set<FaucheEntity> findAllByChamp(String nom);

    @Query("select f.annee from Fauche f")
    Set<Integer> getAllAnnee();
}
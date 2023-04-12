package be.technobel.ylorth.fermedelacroixblancherest.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Culture;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Fauche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FaucheRepository extends JpaRepository<Fauche,Long> {


    boolean existsByAnneeAndCultureId(int annee, Long culture);

    @Query("select f from Fauche f where (f.annee=?1 and f.culture.id=?2)")
    Fauche findByAnneeAndCulture(int annee, Long idCulture);

    @Query("select f from Fauche f where f.annee=?1")
    Set<Fauche> findAllByAnnee(int annee);

    @Query("select f from Fauche f where f.culture.champ.lieu=?1")
    Set<Fauche> findAllByChamp(String nom);

    @Query("select f.annee from Fauche f")
    Set<Integer> getAllAnnee();
}
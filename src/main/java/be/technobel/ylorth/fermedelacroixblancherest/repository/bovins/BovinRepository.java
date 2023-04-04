package be.technobel.ylorth.fermedelacroixblancherest.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface BovinRepository extends JpaRepository<Bovin,Long> {

    Optional<Bovin> findByNumeroInscription(String numeroInscription);
    @Query("select b from Bovin b where b.mereNI= ?1")
    Set<Bovin> findAllEnfantsMere(String mere);
    @Query("select b from Bovin b where b.pereNI= ?1")
    Set<Bovin> findAllEnfantsPere(String pere);

    @Transactional
    @Modifying
    @Query(value="UPDATE public.bovin SET dtype = ?2 WHERE bovin_id =?1", nativeQuery = true)
    void changeType(Long id, String type);

}
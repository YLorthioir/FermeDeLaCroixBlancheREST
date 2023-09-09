package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface BovinRepository extends JpaRepository<BovinEntity,Long> {

    Optional<BovinEntity> findByNumeroInscription(String numeroInscription);
    @Query("select b from Bovin b where b.mereNI= ?1")
    Set<BovinEntity> findAllEnfantsMere(String mere);
    @Query("select b from Bovin b where b.pereNI= ?1")
    Set<BovinEntity> findAllEnfantsPere(String pere);

    @Query("select b from Bovin b where b.sexe='M' and b.nom!=null ")
    Set<BovinEntity> findAllTaureau();

    boolean existsByNumeroInscription(String numeroInscription);

    @Transactional
    @Modifying
    @Query(value="UPDATE public.bovin SET dtype = ?2 WHERE bovin_id =?1", nativeQuery = true)
    void changeType(Long id, String type);

}
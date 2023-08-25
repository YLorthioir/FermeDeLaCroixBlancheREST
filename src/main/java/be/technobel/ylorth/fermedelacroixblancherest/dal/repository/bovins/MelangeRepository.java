package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MelangeRepository extends JpaRepository<MelangeEntity,Long> {
    boolean existsMelangeByNomMelange(String nom);
    Optional<MelangeEntity> findByNomMelange(String nom);
}
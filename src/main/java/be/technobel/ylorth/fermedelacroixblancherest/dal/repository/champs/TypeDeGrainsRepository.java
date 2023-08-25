package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDeGrainsRepository extends JpaRepository<TypeDeGrainEntity,Long> {
    boolean existsByNomGrain(String nom);
}
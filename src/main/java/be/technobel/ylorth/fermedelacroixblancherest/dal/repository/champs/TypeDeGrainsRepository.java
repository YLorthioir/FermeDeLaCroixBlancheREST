package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeDeGrainsRepository extends JpaRepository<TypeDeGrainEntity,Long>, JpaSpecificationExecutor<TypeDeGrainEntity> {
    //boolean existsByNomGrain(String nom);
}
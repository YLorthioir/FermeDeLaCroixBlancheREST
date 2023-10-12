package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChampRepository extends JpaRepository<ChampEntity,Long>, JpaSpecificationExecutor<ChampEntity> {
    //boolean existsByLieu(String lieu);
}
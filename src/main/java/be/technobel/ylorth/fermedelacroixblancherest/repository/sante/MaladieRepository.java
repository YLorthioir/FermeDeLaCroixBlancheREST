package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaladieRepository extends JpaRepository<Maladie,Long> {

}
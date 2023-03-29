package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraitementRepository extends JpaRepository<Traitement,Long> {

}
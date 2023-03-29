package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Vaccin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinRepository extends JpaRepository<Vaccin,Long> {

}
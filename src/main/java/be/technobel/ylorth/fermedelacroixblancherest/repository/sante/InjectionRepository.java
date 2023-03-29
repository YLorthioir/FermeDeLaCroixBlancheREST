package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Injection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionRepository extends JpaRepository<Injection,Long> {

}
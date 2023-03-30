package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.A;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Injection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ARepository extends JpaRepository<A,Long> {
    Set<A> findAllByBovinsId(Long id);
}
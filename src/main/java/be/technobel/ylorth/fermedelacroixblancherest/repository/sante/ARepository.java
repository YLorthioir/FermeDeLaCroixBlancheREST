package be.technobel.ylorth.fermedelacroixblancherest.repository.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.A;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ARepository extends JpaRepository<A,Long> {
    Set<A> findAllByBovinId(Long id);
}
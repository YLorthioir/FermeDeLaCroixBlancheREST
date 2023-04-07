package be.technobel.ylorth.fermedelacroixblancherest.repository.auth;


import be.technobel.ylorth.fermedelacroixblancherest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);


}

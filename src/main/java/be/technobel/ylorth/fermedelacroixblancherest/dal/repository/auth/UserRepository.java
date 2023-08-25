package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.auth;


import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByLogin(String login);

    Optional<UserEntity> findByLogin(String login);

}

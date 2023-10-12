package be.technobel.ylorth.fermedelacroixblancherest.dal.repository.auth;


import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    //boolean existsByLogin(String login);

    // Optional<UserEntity> findByLogin(String login);

}

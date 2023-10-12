package be.technobel.ylorth.fermedelacroixblancherest.bll.service.auth;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.auth.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge les détails de l'utilisateur à partir de la base de données en fonction de son nom d'utilisateur (login).
     *
     * Cette méthode est utilisée par le gestionnaire d'authentification pour charger les détails d'un utilisateur à partir de la base de données en fonction de son nom d'utilisateur (login). Elle recherche l'utilisateur correspondant au nom d'utilisateur fourni dans la base de données en utilisant le référentiel UserRepository. Si l'utilisateur est trouvé, ses détails (UserDetails) sont renvoyés. Si l'utilisateur n'est pas trouvé, une exception de type UsernameNotFoundException est levée.
     *
     * @param username Le nom d'utilisateur (login) de l'utilisateur à charger.
     * @return Les détails de l'utilisateur (UserDetails) si l'utilisateur est trouvé.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé dans la base de données, cette exception est levée.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Specification<UserEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("login"),username)));

        return userRepository.findOne(spec)
                .orElseThrow( () -> new UsernameNotFoundException("couldn't find user with login " + username) );
    }

}

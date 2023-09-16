package be.technobel.ylorth.fermedelacroixblancherest.bll.service.auth;

import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.AuthResponse;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.RegisterForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.auth.UserRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.utils.config.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Enregistre un nouvel utilisateur dans le système.
     *
     * Cette méthode permet d'enregistrer un nouvel utilisateur dans le système en utilisant les informations fournies dans le formulaire d'inscription. Elle vérifie d'abord si un utilisateur avec le même login existe déjà dans la base de données. Si c'est le cas, une exception est levée. Sinon, un nouvel utilisateur est créé avec le rôle spécifié, le mot de passe haché, le login et l'état actif, puis enregistré dans la base de données.
     *
     * @param form Le formulaire d'inscription contenant les informations de l'utilisateur à enregistrer.
     * @throws AlreadyExistsException Si un utilisateur avec le même login existe déjà dans la base de données.
     */
    @Override
    public void register(RegisterForm form) {
        if(userRepository.existsByLogin(form.getLogin()))
            throw new AlreadyExistsException("Le login existe déjà");

        UserEntity entity = new UserEntity();
        entity.setRole(form.getRole());
        entity.setPassword(passwordEncoder.encode(form.getPassword()));
        entity.setLogin(form.getLogin());
        entity.setEnabled(true);
        userRepository.save(entity);

    }

    /**
     * Authentifie un utilisateur avec les informations de connexion fournies.
     *
     * Cette méthode prend en charge le processus d'authentification en utilisant les informations de connexion fournies dans le formulaire de connexion (LoginForm). Elle commence par afficher les informations de connexion à des fins de débogage, puis utilise le gestionnaire d'authentification (authenticationManager) pour tenter d'authentifier l'utilisateur en utilisant les informations de connexion fournies. Si l'authentification réussit, elle génère un jeton JWT (JSON Web Token) pour l'utilisateur authentifié, puis renvoie une réponse d'authentification (AuthResponse) contenant le jeton JWT, le login de l'utilisateur et son rôle.
     *
     * @param form Le formulaire de connexion contenant les informations de connexion de l'utilisateur.
     * @return Une réponse d'authentification (AuthResponse) contenant le jeton JWT, le login de l'utilisateur et son rôle.
     */
    @Override
    public AuthResponse login(LoginForm form) {
        System.out.println("Service:"+ form);
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(form.getLogin(),form.getPassword()) );

        UserEntity user = userRepository.findByLogin(form.getLogin() )
                .orElseThrow();

        String token = jwtProvider.generateToken(user.getUsername(), user.getRole() );

        return AuthResponse.builder()
                .token(token)
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }

}

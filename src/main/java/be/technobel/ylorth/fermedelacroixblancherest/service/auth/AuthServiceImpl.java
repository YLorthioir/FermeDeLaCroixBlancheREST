package be.technobel.ylorth.fermedelacroixblancherest.service.auth;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.AuthDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.User;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.auth.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.auth.RegisterForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.auth.UserRepository;
import be.technobel.ylorth.fermedelacroixblancherest.utils.JwtProvider;
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

    @Override
    public void register(RegisterForm form) {
        if(userRepository.existsByLogin(form.getLogin()))
            throw new AlreadyExistsException("Le login existe déjà");

        User entity = new User();
        entity.setRole(form.getRole());
        entity.setPassword(passwordEncoder.encode(form.getPassword()));
        entity.setLogin(form.getLogin());
        entity.setEnabled(true);
        userRepository.save(entity);

    }

    @Override
    public AuthDTO login(LoginForm form) {
        System.out.println("Service:"+ form);
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(form.getLogin(),form.getPassword()) );

        User user = userRepository.findByLogin(form.getLogin() )
                .orElseThrow();

        String token = jwtProvider.generateToken(user.getUsername(), user.getRole() );

        return AuthDTO.builder()
                .token(token)
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }

}

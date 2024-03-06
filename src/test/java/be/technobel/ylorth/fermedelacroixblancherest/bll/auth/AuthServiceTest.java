package be.technobel.ylorth.fermedelacroixblancherest.bll.auth;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.auth.AuthServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserEntity;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.security.UserRole;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.auth.UserRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.AuthResponse;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.RegisterForm;

import be.technobel.ylorth.fermedelacroixblancherest.pl.utils.config.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtProvider jwtProvider;

    @Test
    public void testRegister_OK() {
        RegisterForm form = new RegisterForm("User", "Password", "Password", "admin");

        when(userRepository.exists(any(Specification.class))).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.register(form);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_WhenUserExists() {
        RegisterForm form = new RegisterForm("User", "Password", "Password", "admin");

        when(userRepository.exists(any(Specification.class))).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () ->
                userService.register(form));

        assertEquals("Le login existe déjà", exception.getMessage());
    }

    @Test
    public void testLogin_Ok() {
        LoginForm form = new LoginForm("user", "password");

        UserEntity user = new UserEntity();
        user.setLogin("user");
        user.setPassword("password");
        user.setRole(UserRole.ADMIN);

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));
        when(jwtProvider.generateToken(anyString(), any(UserRole.class))).thenReturn("token");

        AuthResponse authResponse = userService.login(form);

        assertEquals("user", authResponse.login());
        assertEquals("token", authResponse.token());
        assertEquals(UserRole.ADMIN, authResponse.role());
    }

    @Test
    public void testLogin_WhenAuthenticationFails() {
        LoginForm form = new LoginForm("user", "password");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Login ou mot de passe incorrects"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () ->
                userService.login(form));

        assertEquals("Login ou mot de passe incorrects", exception.getMessage());
    }

    @Test
    public void testLogin_WhenUserNotFound() {
        LoginForm form = new LoginForm("user", "password");

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                userService.login(form));

        assertEquals("User not found", exception.getMessage());
    }
    
}

package be.technobel.ylorth.fermedelacroixblancherest.bll.service.auth;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.AuthResponse;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.RegisterForm;

public interface AuthService {
    void register(RegisterForm form);
    AuthResponse login(LoginForm form);

}

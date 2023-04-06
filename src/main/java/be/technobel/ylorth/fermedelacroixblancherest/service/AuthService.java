package be.technobel.ylorth.fermedelacroixblancherest.service;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.AuthDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.RegisterForm;

public interface AuthService {
    void register(RegisterForm form);
    AuthDTO login(LoginForm form);
    Long findByLogin(String login);

}

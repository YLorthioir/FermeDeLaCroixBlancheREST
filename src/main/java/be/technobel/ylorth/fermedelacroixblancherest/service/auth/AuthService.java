package be.technobel.ylorth.fermedelacroixblancherest.service.auth;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.AuthDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.auth.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.auth.RegisterForm;

public interface AuthService {
    void register(RegisterForm form);
    AuthDTO login(LoginForm form);

}

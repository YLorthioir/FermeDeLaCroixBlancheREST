package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.AuthResponse;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.LoginForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.security.RegisterForm;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginForm form){
        return ResponseEntity.ok(authService.login(form));
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterForm form){
        authService.register(form);
    }
}

package be.technobel.ylorth.fermedelacroixblancherest.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {

        http.csrf().disable();

        http.httpBasic().disable();

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(
            registry -> registry

                    //Authentification

                    .requestMatchers(HttpMethod.POST,"/auth/login").anonymous()
                    .requestMatchers(HttpMethod.POST,"/auth/register").hasAnyRole("ADMIN","GERANT")

                    //Bovin

                    .requestMatchers(HttpMethod.GET,"/bovin/all").authenticated()
                    .requestMatchers(HttpMethod.POST,"/bovin/add").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/{numeroInscription}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/enfants/{numeroInscription}").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/bovin/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/reproduction/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/engraissement/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/type/{id:[0-9]+}").authenticated()

                    //Race

                    .requestMatchers(HttpMethod.GET,"/bovin/race/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/race/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/bovin/race/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/bovin/race/add").authenticated()

                    //Mélange

                    .requestMatchers(HttpMethod.GET,"/bovin/melange/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/melange/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/bovin/melange/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/bovin/melange/add").authenticated()

                    //Vaccin

                    .requestMatchers(HttpMethod.GET,"/sante/vaccination/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/vaccination/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/sante/vaccin/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/vaccin/add").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/vaccin/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/vaccin/{nom}").authenticated()

                    //Maladie

                    .requestMatchers(HttpMethod.GET,"/sante/maladie/a/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/maladie/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/maladie/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/maladie/add").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/sante/maladie/{id:[0-9]+}").authenticated()

                    //Traitement

                    .requestMatchers(HttpMethod.GET,"/sante/traitement/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/traitement/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/traitement/add").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/sante/traitement/{id:[0-9]+}").authenticated()

                    //Champ

                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                    .requestMatchers(HttpMethod.GET,"/champ/all").authenticated()



                    .requestMatchers( request -> request.getRequestURI().length() > 500 ).denyAll()

                    // Si pas permitAll, swagger ne fonctionne pas
                    .anyRequest().permitAll()

        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

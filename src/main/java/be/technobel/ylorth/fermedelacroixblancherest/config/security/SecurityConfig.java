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
                    .requestMatchers(HttpMethod.POST,"/bovin/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/bovin/{numeroInscription}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/enfants/{numeroInscription}").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/bovin/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/bovin/reproduction/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/engraissement/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/type/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/taureaux").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/exists/{numeroInscription}").authenticated()

                    //Race

                    .requestMatchers(HttpMethod.GET,"/bovin/race/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/race/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/bovin/race/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.POST,"/bovin/race/add").hasAnyRole("ADMIN","GERANT")

                    //Mélange

                    .requestMatchers(HttpMethod.GET,"/bovin/melange/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/melange/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/bovin/melange/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.POST,"/bovin/melange/add").hasAnyRole("ADMIN","GERANT")

                    //Vaccin

                    .requestMatchers(HttpMethod.GET,"/sante/vaccination/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/vaccination/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/sante/vaccin/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.POST,"/sante/vaccin/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/sante/vaccin/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/vaccin/{nom}").authenticated()

                    //Maladie

                    .requestMatchers(HttpMethod.GET,"/sante/maladie/a/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/maladie/a/one/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/maladie/a/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/sante/maladie/a/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.DELETE,"/sante/maladie/a/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/sante/maladie/a/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/maladie/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/maladie/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/maladie/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/sante/maladie/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")

                    //Traitement

                    .requestMatchers(HttpMethod.GET,"/sante/traitement/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/sante/traitement/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/sante/traitement/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/sante/traitement/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")

                    //Champ

                    .requestMatchers(HttpMethod.GET,"/champ/all").authenticated()
                    .requestMatchers(HttpMethod.POST,"/champ/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/champ/update/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/champ/one/{id:[0-9]+}").authenticated()

                    //Culture

                    .requestMatchers(HttpMethod.GET,"/champ/culture/all/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.POST,"/champ/culture/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/champ/culture/update/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/champ/culture/one/{id:[0-9]+}").authenticated()

                    //Grains

                    .requestMatchers(HttpMethod.GET,"/champ/grain/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/champ/grain/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/champ/grain/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.POST,"/champ/grain/add").hasAnyRole("ADMIN","GERANT")

                    // Fauche

                    .requestMatchers(HttpMethod.GET,"/champ/fauche/allChamp/{nomChamp}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/champ/fauche/allAnnee/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/champ/fauche/allAnnee").authenticated()
                    .requestMatchers(HttpMethod.POST,"/champ/fauche/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.PATCH,"/champ/fauche/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/champ/fauche/{id:[0-9]+}").authenticated()



                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
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

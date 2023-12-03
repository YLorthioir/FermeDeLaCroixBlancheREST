package be.technobel.ylorth.fermedelacroixblancherest.pl.utils.config.security;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@ConfigurationProperties("app.cors.config")
public class SecurityConfig {

    @Setter
    private String host;
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfiguration());

        http.httpBasic().disable();

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(
            registry -> registry

                    //Authentification

                    .requestMatchers(HttpMethod.POST,"/auth/login").anonymous()
                    .requestMatchers(HttpMethod.POST,"/auth/register").hasAnyRole("ADMIN","GERANT")

                    //BovinEntity

                    .requestMatchers(HttpMethod.GET,"/bovin/all").authenticated()
                    .requestMatchers(HttpMethod.POST,"/bovin/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/bovin/{numeroInscription}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/enfants/{numeroInscription}").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/bovin/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.GET,"/bovin/reproduction/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/engraissement/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/type/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/taureaux").authenticated()
                    .requestMatchers(HttpMethod.GET,"/bovin/bovinEngraissement").authenticated()


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
                    .requestMatchers(HttpMethod.GET,"/sante/vaccination/liste/{id:[0-9]+}").authenticated()

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

                    // Vente BovinEntity

                    .requestMatchers(HttpMethod.GET,"/vente/bovin/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/vente/bovin/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/vente/bovin/update/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.POST,"/vente/bovin/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.DELETE,"/vente/bovin/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")

                    // Vente fauche

                    .requestMatchers(HttpMethod.GET,"/vente/fauche/all").authenticated()
                    .requestMatchers(HttpMethod.GET,"/vente/fauche/{id:[0-9]+}").authenticated()
                    .requestMatchers(HttpMethod.PATCH,"/vente/fauche/update/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.POST,"/vente/fauche/add").hasAnyRole("ADMIN","GERANT")
                    .requestMatchers(HttpMethod.DELETE,"/vente/fauche/{id:[0-9]+}").hasAnyRole("ADMIN","GERANT")

                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                    .requestMatchers( request -> request.getRequestURI().length() > 500 ).denyAll()

                    // swagger
                    //.anyRequest().permitAll()

                    .anyRequest().authenticated()

        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(host));

        config.setAllowedHeaders(List.of("*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

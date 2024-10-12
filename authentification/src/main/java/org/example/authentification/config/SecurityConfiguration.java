package org.example.authentification.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    // Liste blanche pour permettre un accès libre à certaines URL
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/verify/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    // Injecte les composants nécessaires pour l'authentification
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    // Bean pour la configuration du filtre de sécurité
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // URL accessibles sans authentification
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        // Autorisation par rôles spécifiques
                      // .requestMatchers("/doctors/**").hasAnyRole("ADMIN", "DOCTOR") // Accessible aux rôles ADMIN et DOCTOR
                       //.requestMatchers("/patients/**").hasAnyRole("DOCTOR", "PATIENT") // Accessible aux rôles DOCTOR et PATIENT
                        //.requestMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN") // Accessible uniquement au rôle ADMIN
                        .anyRequest().authenticated()
                )
                .cors().and()
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // Pas de session maintenue sur le serveur
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Ajoute le filtre d'authentification JWT
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }






   /* private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/verify/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                /* .requestMatchers("/api/v1/doctors/**").hasAnyRole(Admin.name(), Doctor.name())
                                .requestMatchers(GET, "/api/v1/doctors/**").hasAnyAuthority(Admin_READ.name(), Doctor_READ.name())
                                .requestMatchers(POST, "/api/v1/doctors/**").hasAnyAuthority(Admin_CREATE.name(), Doctor_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/doctors/**").hasAnyAuthority(Admin_UPDATE.name(), Doctor_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/doctors/**").hasAnyAuthority(Admin_DELETE.name(), Doctor_DELETE.name())

                                .requestMatchers("/api/v1/patient/**").hasAnyRole(Doctor.name(), Patient.name())
                                .requestMatchers(GET, "/api/v1/patient/**").hasAnyAuthority(Doctor_READ.name(), Patient_READ.name())
                                .requestMatchers(POST, "/api/v1/patient/**").hasAnyAuthority(Doctor_CREATE.name(), Patient_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/patient/**").hasAnyAuthority(Doctor_UPDATE.name(), Patient_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/patient/**").hasAnyAuthority(Doctor_DELETE.name(), Patient_DELETE.name())

                                .requestMatchers("/api/v1/admin/**").hasRole(Admin.name())
                                .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(Admin_READ.name())
                                .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(Admin_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(Admin_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(Admin_DELETE.name()*/
                             /*   .anyRequest()
                                .authenticated()
                )
                .cors().and()
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }*/
}
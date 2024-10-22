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

import static org.example.authentification.User.Permission.*;
import static org.example.authentification.User.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/chat-socket/**" ,
            "/topic/**" ,
            "/app/**" ,

            "/api/v1/auth/**",
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

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                                .requestMatchers("/api/v1/doctor/**").hasAnyRole(ADMIN.name(), DOCTOR.name())
                                .requestMatchers(GET, "/api/v1/doctor/**").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name())
                                .requestMatchers(POST, "/api/v1/doctor/**").hasAnyAuthority(ADMIN_CREATE.name(), DOCTOR_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/doctor/**").hasAnyAuthority(ADMIN_UPDATE.name(), DOCTOR_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/doctor/**").hasAnyAuthority(ADMIN_DELETE.name(), DOCTOR_DELETE.name())

                                .requestMatchers("/api/v1/patient/**").hasAnyRole(ADMIN.name(), PATIENT.name())
                                .requestMatchers(GET, "/api/v1/patient/**").hasAnyAuthority(ADMIN_READ.name(), PATIENT_READ.name())
                                .requestMatchers(POST, "/api/v1/patient/**").hasAnyAuthority(ADMIN_CREATE.name(), PATIENT_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/patient/**").hasAnyAuthority(ADMIN_UPDATE.name(), PATIENT_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/patient/**").hasAnyAuthority(ADMIN_DELETE.name(), PATIENT_DELETE.name())

                                .requestMatchers("/api/v1/nurse/**").hasAnyRole(ADMIN.name(), NURSE.name())
                                .requestMatchers(GET, "/api/v1/nurse/**").hasAnyAuthority(ADMIN_READ.name(), NURSE_READ.name())
                                .requestMatchers(POST, "/api/v1/nurse/**").hasAnyAuthority(ADMIN_CREATE.name(), NURSE_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/nurse/**").hasAnyAuthority(ADMIN_UPDATE.name(), NURSE_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/nurse/**").hasAnyAuthority(ADMIN_DELETE.name(), NURSE_DELETE.name())

                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())



                                .anyRequest()
                                .authenticated()
                )
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
    }


}
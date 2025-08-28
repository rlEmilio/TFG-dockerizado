package com.tfg.koeshiru.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tfg.koeshiru.auth.filter.JwtAuthenticationFilter;
import com.tfg.koeshiru.auth.filter.JwtValidationFilter;
import com.tfg.koeshiru.repositories.UsuarioRepository;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios").permitAll()
                .requestMatchers("/api/usuarios/{id}").permitAll()
                .requestMatchers("/api/actores").permitAll()
                .requestMatchers("/api/actores/{id}").permitAll()
                .requestMatchers("/api/clubs").permitAll()
                .requestMatchers("/api/clubs/{id}").permitAll()
                .requestMatchers("/api/mensajes").permitAll()
                .requestMatchers("/api/mensajes/{id}").permitAll()
                .requestMatchers("/api/productos").permitAll()
                .requestMatchers("/api/productos/{id}").permitAll()
                .requestMatchers("/api/trabajos").permitAll()
                .requestMatchers("/api/trabajos/{id}").permitAll()
                .anyRequest().permitAll())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), usuarioRepository))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Permitir todos los orígenes (solo para pruebas)
        //config.setAllowedOriginPatterns(Arrays.asList("*"));

        config.setAllowedOrigins(Arrays.asList("http://koeshiru.com",
                "http://frontend:80",
                "http://localhost:8081",
                "http://194.164.164.200",
                "http://127.0.0.1:25598",
                "http://127.0.0.7:5500",
                "http://localhost:8080",
                "http://localhost:5500"));

        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(this.configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}

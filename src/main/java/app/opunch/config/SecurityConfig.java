package app.opunch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// TEMPORAL - Bypass de Spring Security para que no nos obligue a loguearnos por ahora
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Strength de 10 es el equilibrio ideal entre seguridad y rendimiento
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para que tus formularios funcionen fácil
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite el acceso total a todas las rutas
                );
        return http.build();
    }
}
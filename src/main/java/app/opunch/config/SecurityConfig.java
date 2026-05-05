package app.opunch.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/setup/**").permitAll() // Recursos estáticos
                        .requestMatchers("/admin/**", "/dashboard", "/users").hasRole("ADMIN") // Solo administradores
                        .requestMatchers("/scanner/**").hasRole("SCANNER") // Estación de fichaje
                        .anyRequest().authenticated() // El resto requiere login
                )
                .formLogin(form -> form
                        .loginPage("/login") // Nuestra página personalizada
                        .usernameParameter("username") // Debe coincidir con el name="" de tu <input> en HTML
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL que activará el cierre de sesión
                        .logoutSuccessUrl("/login?logout") // Redirección tras éxito
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
package app.opunch.service;

import app.opunch.config.UserCustomDetails; // Importante importar tu clase
import app.opunch.model.User;
import app.opunch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscamos al usuario en la base de datos (01-schema_19.sql)[cite: 10]
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // LOG DE DEPURACIÓN (Solo para desarrollo)
        System.out.println("DEBUG: Cargando usuario " + user.getUsername() + " con rol ID: " + user.getRole());

        // 2. RETORNO CRÍTICO: Usamos nuestro adaptador personalizado
        // Esto permite que el SuccessHandler acceda al getId() y al switch de roles corregido.
        return new UserCustomDetails(user);
    }
}
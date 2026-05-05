package app.opunch.config;

import app.opunch.model.User; // Tu entidad de usuario
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/users");
        } else if (roles.contains("ROLE_SCANNER")) {
            response.sendRedirect("/scanner");
        } else if (roles.contains("ROLE_USER")) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserCustomDetails details) {
                response.sendRedirect("/users/profile/" + details.getId());
            } else {
                // Si el principal no es de nuestro tipo personalizado, algo va mal
                response.sendRedirect("/login?error=auth_error");
            }
        } else {
            // Caso de seguridad: Usuario sin rol reconocido
            response.sendRedirect("/login?error=no_role");
        }
    }
}
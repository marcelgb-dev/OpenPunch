package app.opunch.config;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        // Esto te confirmará si el login llega a tener éxito antes del redirect
        System.out.println("✅ LOGIN EXITOSO: " + success.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent failure) {
        // Esto se dispara si Spring considera que las credenciales no valen
        System.out.println("❌ ERROR DE LOGIN: Usuario o contraseña incorrectos para: "
                + failure.getAuthentication().getName());
    }
}

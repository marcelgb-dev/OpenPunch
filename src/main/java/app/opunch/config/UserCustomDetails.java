package app.opunch.config;

import app.opunch.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Adaptador de seguridad para OpenPunch.
 * Implementa la lógica de roles académicos: 1-Admin, 2-Scanner, 3-User.
 */
public class UserCustomDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final Integer role;
    private final String name;

    public UserCustomDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.name = user.getName();
    }

    /**
     * Retorna el ID para redirecciones dinámicas en el SuccessHandler.
     * Requisito para: /profile/{id}
     */
    public Integer getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mapeo según la nueva jerarquía solicitada
        String roleName = switch (this.role) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_SCANNER";
            case 3 -> "ROLE_USER";
            default -> "ROLE_USER";
        };
        return AuthorityUtils.createAuthorityList(roleName);
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    // Métodos de estado (Planteamiento.md - Control de acceso profesional)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
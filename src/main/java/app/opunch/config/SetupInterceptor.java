package app.opunch.config;

import app.opunch.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SetupInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Evitamos interceptar la propia página de setup o recursos estáticos
        String path = request.getRequestURI();
        if (path.startsWith("/setup") || path.startsWith("/css") || path.startsWith("/js")) {
            return true;
        }

        // Contamos usuarios en la BD
        Integer adminCount = userService.getUsersByRole(1).size();
        System.out.println("Current number of admins: " + adminCount);

        if (adminCount != null && adminCount == 0) {
            response.sendRedirect("/setup/admin");
            return false;
        }

        return true;
    }
}
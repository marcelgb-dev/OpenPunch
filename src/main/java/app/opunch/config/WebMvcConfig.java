package app.opunch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SetupInterceptor setupInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registramos el interceptor para que evalúe todas las peticiones
        registry.addInterceptor(setupInterceptor)
                .addPathPatterns("/**") // Se aplica a todas las URLs
                .excludePathPatterns(
                        "/setup/**",        // No interceptar la propia página de setup
                        "/css/**",          // No interceptar recursos estáticos
                        "/js/**",
                        "/images/**",
                        "/favicon.ico"
                );
    }
}
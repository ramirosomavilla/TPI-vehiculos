package serviciopruebas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;


import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig implements Filter {

    private static final String ALLOWED_ORIGINS = "http://localhost:8080";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req; // Cast to HttpServletRequest
        HttpServletResponse response = (HttpServletResponse) res; // Cast to HttpServletResponse

        String origin = request.getHeader("Origin");

        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            System.out.println(origin);
            chain.doFilter(req, res);

        } else {
            System.out.println(origin);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
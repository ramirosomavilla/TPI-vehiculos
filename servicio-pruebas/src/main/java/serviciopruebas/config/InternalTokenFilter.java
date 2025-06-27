package serviciopruebas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class InternalTokenFilter extends OncePerRequestFilter {
  @Value("${internal.token}")
  private String internalToken;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader("X-Internal-Token");
    if (token != null && token.equals(internalToken)) {
      filterChain.doFilter(request, response);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized: Invalid internal token");
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    AntPathMatcher matcher = new AntPathMatcher();
    return matcher.match("/swagger-ui.html", path)
        || matcher.match("/swagger-ui/**", path)
        || matcher.match("/v3/api-docs", path)
        || matcher.match("/v3/api-docs/**", path)
        || matcher.match("/api-docs", path)
        || matcher.match("/api-docs/**", path)
        || matcher.match("/swagger-resources/**", path)
        || matcher.match("/webjars/**", path);
  }
}
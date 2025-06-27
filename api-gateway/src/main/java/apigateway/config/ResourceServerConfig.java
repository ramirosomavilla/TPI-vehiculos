package apigateway.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class ResourceServerConfig {
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers(HttpMethod.GET, "/api/v1/pruebas/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.POST, "/api/v1/pruebas/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.PUT, "/api/v1/pruebas/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.DELETE, "/api/v1/pruebas/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.GET, "/api/v1/notificaciones/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.POST, "/api/v1/notificaciones/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.PUT, "/api/v1/notificaciones/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.DELETE, "/api/v1/notificaciones/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.GET, "/api/v1/usuarios/interesados/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.POST, "/api/v1/usuarios/interesados/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.PUT, "/api/v1/usuarios/interesados/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.DELETE, "/api/v1/usuarios/interesados/**").hasAnyRole("EMPLEADO", "ADMIN")
            .pathMatchers(HttpMethod.GET, "/api/v1/reportes/**").hasAnyRole("ADMIN")
            .pathMatchers(HttpMethod.POST, "/api/v1/vehiculos/**").hasAnyRole("VEHICULO")

            .anyExchange().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
    return http.build();
  }

  private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    return new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {
      @Override
      public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess != null && realmAccess.get("roles") instanceof Collection) {
          Collection<String> roles = (Collection<String>) realmAccess.get("roles");
          for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
          }
        }
        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
      }
    };
  }
}

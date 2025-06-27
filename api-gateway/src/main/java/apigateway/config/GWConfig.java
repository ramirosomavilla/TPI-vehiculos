package apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
            @Value("${tpi-vehiculos-api-gw.url-microservicio-pruebas}") String uriPruebas,
            @Value("${tpi-vehiculos-api-gw.url-microservicio-usuarios}") String uriUsuarios,
            @Value("${tpi-vehiculos-api-gw.url-microservicio-reportes}") String uriReportes,
            @Value("${internal.token}") String internalToken) {
        return builder.routes()
                // Ruteo al Microservicio de Notificaciones
                .route(p -> p.path("/api/v1/notificaciones/**")
                        .filters(f -> f.addRequestHeader("X-Internal-Token", internalToken))
                        .uri(uriUsuarios))
                // Ruteo al Microservicio de Pruebas
                .route(p -> p.path("/api/v1/pruebas/**")
                        .filters(f -> f.addRequestHeader("X-Internal-Token", internalToken))
                        .uri(uriPruebas))
                // Ruteo al Microservicio de Reportes
                .route(p -> p.path("/api/v1/reportes/**")
                        .filters(f -> f.addRequestHeader("X-Internal-Token", internalToken))
                        .uri(uriReportes))
                // Ruteo al Microservicio de Vehiculos
                .route(p -> p.path("/api/v1/vehiculos/**")
                        .filters(f -> f.addRequestHeader("X-Internal-Token", internalToken))
                        .uri(uriPruebas))
                // Ruteo al Microservicio de Usuarios
                .route(p -> p.path("/api/v1/usuarios/**")
                        .filters(f -> f.addRequestHeader("X-Internal-Token", internalToken))
                        .uri(uriUsuarios))
                .build();

    }

}

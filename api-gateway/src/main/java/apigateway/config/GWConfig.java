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
                                        @Value("${tpi-vehiculos-api-gw.url-microservicio-notificaciones}") String uriNotificaciones,
                                        @Value("${tpi-vehiculos-api-gw.url-microservicio-pruebas}") String uriPruebas,
                                        @Value("${tpi-vehiculos-api-gw.url-microservicio-reportes}") String uriReportes,
                                        @Value("${tpi-vehiculos-api-gw.url-microservicio-vehiculos}") String uriVehiculos,
                                        @Value("${tpi-vehiculos-api-gw.url-microservicio-usuarios}") String uriUsuarios) {
        return builder.routes()
                // Ruteo al Microservicio de Notificaciones
                .route(p -> p.path("/api/notificaciones/**").uri(uriNotificaciones))
                // Ruteo al Microservicio de Pruebas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                // Ruteo al Microservicio de Reportes
                .route(p -> p.path("/api/reportes/**").uri(uriReportes))
                // Ruteo al Microservicio de Vehiculos
                .route(p -> p.path("/api/vehiculos/**").uri(uriVehiculos))
                // Ruteo al Microservicio de Usuarios
                .route(p -> p.path("/api/usuarios/**").uri(uriUsuarios))
                .build();

    }

}

package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import serviciopruebas.dtos.NotificacionRequest;

@Component
public class NotificacionClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    public void notificarInteresado(Integer idEmpleado, Integer idVehiculo, Integer idInteresado, String tipo, String mensaje) {
        NotificacionRequest request = new NotificacionRequest();
        request.setIdEmpleado(idEmpleado);
        request.setIdVehiculo(idVehiculo);
        request.setIdInteresado(idInteresado);
        request.setTipo(tipo);
        request.setMensaje(mensaje);

        System.out.println("usuariosServiceUrl: " + usuariosServiceUrl);
        webClientBuilder.build()
                .post()
                .uri(usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idInteresado + "/notificar")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}

package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import serviciopruebas.dtos.NotificacionRequestDTO;

@Component
public class NotificacionClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    public void notificarInteresado(NotificacionRequestDTO notificacionRequest) {
        Integer idInteresado = notificacionRequest.getIdInteresado();

        webClientBuilder.build()
                .post()
                .uri(usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idInteresado + "/notificar")
                .bodyValue(notificacionRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}

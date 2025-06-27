package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import serviciopruebas.dtos.NotificacionRequestDTO;

@Component
public class NotificacionClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    public void notificarInteresado(NotificacionRequestDTO notificacionRequest) {
        Integer idInteresado = notificacionRequest.getIdInteresado();
        String url = usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idInteresado + "/notificar";
        restTemplate.postForObject(url, notificacionRequest, Void.class);
    }
}

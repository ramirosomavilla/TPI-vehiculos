package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class InteresadoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    public void restringirInteresado(Integer idInteresado) {
        try {
            webClientBuilder.build()
                    .put()
                    .uri(usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idInteresado + "/restringido")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al restringir el interesado: " + idInteresado + e.getStatusCode());
        }
    }

    public boolean interesadoHasExpiredLicense(int idUsuario) {
        try {
            Boolean expired = webClientBuilder.build()
                    .get()
                    .uri(usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idUsuario + "/has-expired-license")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return expired;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error consultando el servicio de usuarios: " + e.getStatusCode());
        }
    }

    public boolean interesadoIsRestricted(int idUsuario) {
        try {
            Boolean restricted = webClientBuilder.build()
                    .get()
                    .uri(usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idUsuario + "/is-restricted")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return restricted;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error consultando el servicio de usuarios: " + e.getStatusCode());
        }
    }
}

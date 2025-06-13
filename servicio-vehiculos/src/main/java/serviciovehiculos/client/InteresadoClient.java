package serviciovehiculos.client;

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
                  .uri(usuariosServiceUrl + "/interesados/"+ idInteresado + "/restringido")
                  .retrieve()
                  .bodyToMono(Void.class)
                  .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al restringir el interesado: " + idInteresado + e.getStatusCode());
        }
    }
}

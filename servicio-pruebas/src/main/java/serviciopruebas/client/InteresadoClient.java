package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InteresadoClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    public void restringirInteresado(Integer idInteresado) {
        try {
            String url = usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idInteresado + "/restringido";
            restTemplate.put(url, null);
        } catch (Exception e) {
            throw new RuntimeException("Error al restringir el interesado: " + idInteresado + " " + e.getMessage());
        }
    }

    public boolean interesadoHasExpiredLicense(int idUsuario) {
        try {
            String url = usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idUsuario + "/has-expired-license";
            Boolean expired = restTemplate.getForObject(url, Boolean.class);
            return expired != null && expired;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean interesadoIsRestricted(int idUsuario) {
        try {
            String url = usuariosServiceUrl + "/api/v1/usuarios/interesados/" + idUsuario + "/is-restricted";
            Boolean restricted = restTemplate.getForObject(url, Boolean.class);
            return restricted != null && restricted;
        } catch (Exception e) {
            return false;
        }
    }
}

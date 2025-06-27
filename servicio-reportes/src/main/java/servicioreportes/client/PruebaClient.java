package servicioreportes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import servicioreportes.dtos.PruebaDTO;
import java.util.Arrays;
import java.util.List;

@Component
public class PruebaClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${pruebas.service.url}")
    private String configServiceUrl;

    public List<PruebaDTO> obtenerPruebasPorVehiculo(Integer vehiculoId) {
        String url = configServiceUrl + "/vehiculos/" + vehiculoId;
        PruebaDTO[] pruebas = restTemplate.getForObject(url, PruebaDTO[].class);
        return pruebas != null ? Arrays.asList(pruebas) : List.of();
    }
}

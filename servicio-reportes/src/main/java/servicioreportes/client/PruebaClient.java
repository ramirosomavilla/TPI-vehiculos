package servicioreportes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import servicioreportes.dtos.PruebaDTO;

import java.util.List;

@Component
public interface PruebaClient {
        @GetMapping("/pruebas/vehiculo/{vehiculoId}")
        List<PruebaDTO> obtenerPruebasPorVehiculo(@PathVariable("vehiculoId") Long vehiculoId);
    }



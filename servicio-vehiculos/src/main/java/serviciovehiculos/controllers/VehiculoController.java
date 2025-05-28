package serviciovehiculos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

    @PostMapping
    public String recibirVehiculo(@RequestBody(required = false) String body) {
        logger.info("¡Petición recibida en /vehiculos! Cuerpo: {}", body);
        return "Vehículo recibido";
    }

    @GetMapping
    public String test() {
        logger.info("¡Petición GET recibida en /vehiculos!");
        return "Servicio de vehículos activo";
    }
} 
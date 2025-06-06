package serviciopruebas.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pruebas")
public class PruebaController {
    private static final Logger logger = LoggerFactory.getLogger(PruebaController.class);

    @PostMapping
    public String recibirPrueba(@RequestBody(required = false) String body) {
        logger.info("¡Petición recibida en /pruebas! Cuerpo: {}", body);
        return "Prueba recibida";
    }

    @GetMapping
    public String test() {
        logger.info("¡Petición GET recibida en /pruebas!");
        return "Servicio de pruebas activo";
    }
} 
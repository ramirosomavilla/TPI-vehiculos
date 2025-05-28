package servicioreportes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private static final Logger logger = LoggerFactory.getLogger(ReporteController.class);

    @PostMapping
    public String recibirReporte(@RequestBody(required = false) String body) {
        logger.info("¡Petición recibida en /reportes! Cuerpo: {}", body);
        return "Reporte recibido";
    }

    @GetMapping
    public String test() {
        logger.info("¡Petición GET recibida en /reportes!");
        return "Servicio de reportes activo";
    }
} 
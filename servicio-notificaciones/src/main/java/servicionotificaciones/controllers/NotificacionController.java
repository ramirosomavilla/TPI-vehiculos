package servicionotificaciones.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    private static final Logger logger = LoggerFactory.getLogger(NotificacionController.class);

    @PostMapping
    public String recibirNotificacion(@RequestBody(required = false) String body) {
        logger.info("¡Petición recibida en /notificaciones! Cuerpo: {}", body);
        return "Notificación recibida";
    }

    @GetMapping
    public String test() {
        logger.info("¡Petición GET recibida en /notificaciones!");
        return "Servicio de notificaciones activo";
    }
}

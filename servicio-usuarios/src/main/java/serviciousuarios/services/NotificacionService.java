package serviciousuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviciousuarios.entities.Notificacion;
import serviciousuarios.repositories.NotificacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public void crearNotificacion(Integer idCliente, Integer idVehiculo, Integer idInteresado, String tipo, String mensaje) {
        Notificacion notificacion = new Notificacion(idCliente, idVehiculo, idInteresado, tipo, mensaje);
        notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerNotificacionesNoLeidasPorTipo(String tipo) {
        return notificacionRepository.findByTipoAndLeidaFalse(tipo);
    }

    public List<Notificacion> obtenerNotificacionesPorTipo(String tipo) {
        return notificacionRepository.findByTipo(tipo);
    }

    public List<Notificacion> obtenerTodasLasNotificacionesNoLeidas() {
        return notificacionRepository.findByLeidaFalse();
    }

    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }

    public void marcarComoLeida(Integer idNotificacion) {
        Optional<Notificacion> notificacion = notificacionRepository.findById(idNotificacion);
        if (notificacion.isPresent()) {
            notificacion.get().setLeida(true);
            notificacionRepository.save(notificacion.get());
        }
    }

    public void marcarComoLeidasPorTipo(String tipo) {
        List<Notificacion> notificaciones = notificacionRepository.findByTipo(tipo);
        for (Notificacion notificacion : notificaciones) {
            notificacion.setLeida(true);
        }
        notificacionRepository.saveAll(notificaciones);
    }
}

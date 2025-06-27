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

    public void crearNotificacion(Integer idCliente, Integer idVehiculo, Integer idInteresado, String tipo,
            String mensaje) {
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

    public Notificacion getNotificacionById(int id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public Notificacion createNotificacion(serviciousuarios.dtos.NotificacionRequestDTO dto) {
        Notificacion notificacion = new Notificacion(dto.getIdEmpleado(), dto.getIdVehiculo(), dto.getIdInteresado(),
                dto.getTipo(), dto.getMensaje());
        return notificacionRepository.save(notificacion);
    }

    public Notificacion updateNotificacion(int id, serviciousuarios.dtos.NotificacionRequestDTO dto) {
        return notificacionRepository.findById(id).map(notif -> {
            notif.setIdEmpleado(dto.getIdEmpleado());
            notif.setIdVehiculo(dto.getIdVehiculo());
            notif.setIdInteresado(dto.getIdInteresado());
            notif.setTipo(dto.getTipo());
            notif.setMensaje(dto.getMensaje());
            return notificacionRepository.save(notif);
        }).orElse(null);
    }

    public Notificacion patchNotificacion(int id, serviciousuarios.dtos.NotificacionRequestDTO dto) {
        return notificacionRepository.findById(id).map(notif -> {
            if (dto.getIdEmpleado() != null)
                notif.setIdEmpleado(dto.getIdEmpleado());
            if (dto.getIdVehiculo() != null)
                notif.setIdVehiculo(dto.getIdVehiculo());
            if (dto.getIdInteresado() != null)
                notif.setIdInteresado(dto.getIdInteresado());
            if (dto.getTipo() != null)
                notif.setTipo(dto.getTipo());
            if (dto.getMensaje() != null)
                notif.setMensaje(dto.getMensaje());
            return notificacionRepository.save(notif);
        }).orElse(null);
    }

    public boolean deleteNotificacion(int id) {
        if (!notificacionRepository.existsById(id))
            return false;
        notificacionRepository.deleteById(id);
        return true;
    }
}

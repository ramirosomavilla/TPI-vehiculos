package serviciousuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serviciousuarios.entities.Notificacion;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer > {
    List<Notificacion> findByTipoAndLeidaFalse(String tipo);
    List<Notificacion> findByTipo(String tipo);
    List<Notificacion> findByLeidaFalse();
}

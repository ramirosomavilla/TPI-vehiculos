package serviciousuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serviciousuarios.entities.Incidente;
import java.util.List;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {
    List<Incidente> findByEmpleadoId(Long empleadoId);
}

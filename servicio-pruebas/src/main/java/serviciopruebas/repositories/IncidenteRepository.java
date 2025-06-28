package serviciopruebas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serviciopruebas.entities.Incidente;
import java.util.List;

public interface IncidenteRepository extends JpaRepository<Incidente, Integer> {
    List<Incidente> findByEmpleadoId(int empleadoId);
}
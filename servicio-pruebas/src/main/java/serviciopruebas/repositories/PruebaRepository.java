package serviciopruebas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serviciopruebas.entities.Prueba;

import java.util.List;
import java.util.Optional;

public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
    boolean existsByIdVehiculoAndFechaHoraFinIsNull(Integer vehiculoId);
    List<Prueba> findByFechaHoraFinIsNull();
    Optional<Prueba> findByIdVehiculoAndFechaHoraFinIsNull(Integer vehiculoId);
    List<Prueba> findByIdVehiculo(Integer vehiculoId);
}

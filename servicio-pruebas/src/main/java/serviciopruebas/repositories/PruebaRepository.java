package serviciopruebas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import serviciopruebas.entities.Prueba;

public interface PruebaRepository extends CrudRepository<Prueba, Integer> {
    boolean existsByIdVehiculoAndFechaHoraFinIsNull(Integer vehiculoId);
    List<Prueba> findByFechaHoraFinIsNull();
    Optional<Prueba> findByIdVehiculoAndFechaHoraFinIsNull(Integer vehiculoId);
}

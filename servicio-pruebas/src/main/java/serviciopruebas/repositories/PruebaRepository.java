package serviciopruebas.repositories;

import org.springframework.data.repository.CrudRepository;
import serviciopruebas.entities.Prueba;

public interface PruebaRepository extends CrudRepository<Prueba, Integer> {
    boolean existsByVehiculoIdAndFechaFinIsNull(Integer vehiculoId);
}

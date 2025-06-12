package serviciovehiculos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serviciovehiculos.entities.Vehiculo;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
  List<Vehiculo> findByPatente(String patente);
} 
package serviciovehiculos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serviciovehiculos.entities.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
} 
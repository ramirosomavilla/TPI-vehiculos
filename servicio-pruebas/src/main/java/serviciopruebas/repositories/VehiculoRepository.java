package serviciopruebas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import serviciopruebas.entities.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
}
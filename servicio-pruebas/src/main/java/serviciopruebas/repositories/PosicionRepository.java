package serviciopruebas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import serviciopruebas.entities.Posicion;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
}

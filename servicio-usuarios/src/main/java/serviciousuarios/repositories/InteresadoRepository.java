package serviciousuarios.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import serviciousuarios.entities.Interesado;

@Repository
public interface InteresadoRepository extends JpaRepository<Interesado, Integer> {

}

package servicioreportes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import servicioreportes.entities.Posicion;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PosicionRepository
        extends JpaRepository<Posicion, Long> {

    @Query("SELECT p FROM Posicion p "
            + "WHERE p.vehiculoId = :vehiculoId "
            + "AND p.fechaHora BETWEEN :inicio AND :fin "
            + "ORDER BY p.fechaHora ASC")
    List<Posicion> findByVehiculoIdAndFechas(
            @Param("vehiculoId") Long vehiculoId,
            @Param("inicio")    LocalDateTime inicio,
            @Param("fin")       LocalDateTime fin);
}


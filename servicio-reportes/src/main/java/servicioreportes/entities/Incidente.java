package servicioreportes.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "incidentes")
@Data
public class Incidente {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "empleado_id", nullable=false)
        private int empleadoId;

        @Column(nullable=false)
        private String descripcion;

        @Column(nullable=false)
        private LocalDateTime fecha;

        @Column(name = "prueba_id", nullable=false)
        private int pruebaId;
    }
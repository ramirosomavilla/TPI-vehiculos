package servicioreportes.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "incidentes")
public class Incidente {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "empleado_id", nullable=false)
        private Long empleadoId;

        @Column(nullable=false)
        private String descripcion;

        @Column(nullable=false)
        private LocalDateTime fecha;

        // Getters y Setters

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public Long getEmpleadoId() { return empleadoId; }
        public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public LocalDateTime getFecha() { return fecha; }
        public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    }
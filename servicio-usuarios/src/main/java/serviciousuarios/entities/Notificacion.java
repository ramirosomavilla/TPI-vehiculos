package serviciousuarios.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notificaciones")
@Data
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private Integer idEmpleado;

    @Column(nullable = false)
    private Integer idVehiculo;

    @Column(nullable = false)
    private Integer idInteresado;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private boolean leida;

    @Column(nullable = false)
    private String mensaje;

    public Notificacion(Integer idEmpleado, Integer idVehiculo, Integer idInteresado, String tipo, String mensaje) {
        this.idEmpleado = idEmpleado;
        this.idVehiculo = idVehiculo;
        this.idInteresado = idInteresado;
        this.tipo = tipo;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
        this.mensaje = mensaje;
    }
}
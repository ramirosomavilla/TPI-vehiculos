package servicioreportes.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Posiciones")
@Data
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_vehiculo", nullable=false)
    private int vehiculoId;

    @Column(name = "fecha_hora", nullable=false)
    private LocalDateTime fechaHora;

    @Column(nullable=false)
    private Double latitud;

    @Column(nullable=false)
    private Double longitud;
}


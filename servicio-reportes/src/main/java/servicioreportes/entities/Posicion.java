package servicioreportes.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Posiciones")
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehiculo_id", nullable=false)
    private Long vehiculoId;

    @Column(name = "fecha_hora", nullable=false)
    private LocalDateTime fechaHora;

    @Column(nullable=false)
    private Double latitud;

    @Column(nullable=false)
    private Double longitud;

    // getters y setters

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

}


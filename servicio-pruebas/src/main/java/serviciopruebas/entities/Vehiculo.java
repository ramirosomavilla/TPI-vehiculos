package serviciopruebas.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vehiculos")
@Data
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableGenerator(name = "Vehiculos", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="Vehiculos",
            initialValue=1, allocationSize=1)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String patente;

    @Column(name = "ID_MODELO", nullable = false)
    private Integer idModelo;

    @Column(nullable = false)
    private Integer anio;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @Column(name = "fecha_ubicacion")
    private LocalDateTime fechaUbicacion;

} 
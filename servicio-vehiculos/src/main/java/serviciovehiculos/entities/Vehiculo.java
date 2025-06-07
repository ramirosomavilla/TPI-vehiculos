package serviciovehiculos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Vehiculos")
@Data
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String patente;

    @Column(name = "ID_MODELO", nullable = false)
    private Integer idModelo;

    @Column(nullable = false)
    private Integer anio = 2019;

} 
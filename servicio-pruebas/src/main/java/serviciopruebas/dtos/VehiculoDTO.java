package serviciopruebas.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehiculoDTO {
    private Integer id;
    private String patente;
    private Integer idModelo;
    private Integer anio;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaUbicacion;
} 
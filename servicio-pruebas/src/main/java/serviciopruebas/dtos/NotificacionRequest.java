package serviciopruebas.dtos;

import lombok.Data;

@Data
public class NotificacionRequest {
    private Integer idEmpleado;
    private Integer idVehiculo;
    private Integer idInteresado;
    private String tipo;
    private String mensaje;
} 
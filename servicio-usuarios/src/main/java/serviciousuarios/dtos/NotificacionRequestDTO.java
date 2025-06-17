package serviciousuarios.dtos;

import lombok.Data;

@Data
public class NotificacionRequestDTO {
    private Integer idEmpleado;
    private Integer idVehiculo;
    private Integer idInteresado;
    private String tipo;
    private String mensaje;    
} 
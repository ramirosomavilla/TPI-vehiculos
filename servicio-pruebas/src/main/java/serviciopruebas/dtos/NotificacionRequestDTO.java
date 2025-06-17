package serviciopruebas.dtos;

import lombok.Data;

@Data
public class NotificacionRequestDTO {
    private Integer idEmpleado;
    private Integer idVehiculo;
    private Integer idInteresado;
    private String tipo;
    private String mensaje;

    public NotificacionRequestDTO(Integer idEmpleado, Integer idVehiculo, Integer idInteresado, String tipo, String mensaje) {
        this.idEmpleado = idEmpleado;
        this.idVehiculo = idVehiculo;
        this.idInteresado = idInteresado;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }
} 
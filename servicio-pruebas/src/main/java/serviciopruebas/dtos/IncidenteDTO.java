package serviciopruebas.dtos;

import lombok.Data;
import serviciopruebas.entities.Incidente;

@Data
public class IncidenteDTO {
    private int id;
    private int empleadoId;
    private String descripcion;
    private String fecha;
    private int pruebaId;
    private String tipo;
    private java.time.LocalDateTime fechaHora;

    public IncidenteDTO(int id, int empleadoId, String descripcion, String fecha, int pruebaId) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.pruebaId = pruebaId;
    }

    public IncidenteDTO(int idEmpleado, String tipo, String descripcion, java.time.LocalDateTime fechaHora,
            int idPrueba) {
        this.empleadoId = idEmpleado;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.pruebaId = idPrueba;
    }

    public static IncidenteDTO toDto(Incidente inc) {
        return new IncidenteDTO(
                inc.getId(),
                inc.getEmpleadoId(),
                inc.getDescripcion(),
                inc.getFecha().toString(),
                inc.getPruebaId());
    }
}

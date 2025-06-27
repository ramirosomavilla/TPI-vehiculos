package servicioreportes.dtos;

import lombok.Data;
import servicioreportes.entities.Incidente;

@Data
public class IncidenteDTO {
    private int id;
    private int empleadoId;
    private String descripcion;
    private String fecha;
    private int pruebaId;

    public IncidenteDTO(int id, int empleadoId, String descripcion, String fecha, int pruebaId) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.pruebaId = pruebaId;
    }

    public static IncidenteDTO toDto(Incidente inc) {
        return new IncidenteDTO(
                inc.getId(),
                inc.getEmpleadoId(),
                inc.getDescripcion(),
                inc.getFecha().toString(),
                inc.getPruebaId()
        );
    }
}

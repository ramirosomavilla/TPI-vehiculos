package servicioreportes.dtos;
import servicioreportes.entities.Empleado;
import servicioreportes.entities.Incidente;

public record IncidenteDTO(
    Long id,
    Long empleadoId,
    String descripcion,
    String fecha
) { }


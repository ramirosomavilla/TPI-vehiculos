package serviciousuarios.dtos;


import serviciousuarios.entities.Empleado;
import serviciousuarios.entities.Incidente;

public record IncidenteDTO(
        Long id,
        Long empleadoId,
        String descripcion,
        String fecha
) { }

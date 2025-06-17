package serviciousuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviciousuarios.dtos.IncidenteDTO;
import serviciousuarios.dtos.KilometrosDTO;
import serviciousuarios.dtos.PruebaDTO;
import serviciousuarios.entities.Incidente;
import serviciousuarios.repositories.IncidenteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ReporteService {

    @Autowired
    private IncidenteRepository incidenteRepository;

    public List<IncidenteDTO> listarIncidentes() {
        return incidenteRepository.findAll().stream()
                .map(inc -> new IncidenteDTO(inc.getId(), inc.getEmpleadoId(), inc.getDescripcion(), inc.getFecha().toString()))
                .collect(Collectors.toList());
    }

    public List<IncidenteDTO> listarIncidentesPorEmpleado(Long idEmpleado) {
        return incidenteRepository.findByEmpleadoId(idEmpleado).stream()
                .map(inc -> new IncidenteDTO(inc.getId(), inc.getEmpleadoId(), inc.getDescripcion(), inc.getFecha().toString()))
                .collect(Collectors.toList());
    }

    public KilometrosDTO calcularKilometrosRecorridos(Long idVehiculo, String desde, String hasta) {
        // TODO: Implementar lógica real de cálculo
        return new KilometrosDTO(idVehiculo, 123.4); // Valor simulado
    }

    public List<PruebaDTO> listarPruebasPorVehiculo(Long idVehiculo) {
        // TODO: Consumir microservicio de pruebas para obtener datos reales
        return List.of(); // Valor simulado
    }
}

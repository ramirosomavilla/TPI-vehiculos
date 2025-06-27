package servicioreportes.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import servicioreportes.dtos.IncidenteDTO;
import servicioreportes.dtos.KilometrosDTO;
import servicioreportes.dtos.PruebaDTO;
import servicioreportes.services.ReporteServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Operaciones de consulta de reportes, incidentes y kilometraje")
public class ReporteController {
    private final ReporteServices reporteService;

    public ReporteController(ReporteServices reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/incidentes")
    @Operation(summary = "Obtener todos los incidentes", description = "Devuelve una lista de todos los incidentes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de incidentes obtenida exitosamente", content = @Content(schema = @Schema(implementation = IncidenteDTO.class)))
    public List<IncidenteDTO> obtenerTodosLosIncidentes() {
        return reporteService.listarIncidentes();
    }

    @GetMapping("/incidentes/empleado/{idEmpleado}")
    @Operation(summary = "Obtener incidentes por empleado", description = "Devuelve una lista de incidentes asociados a un empleado específico.")
    @ApiResponse(responseCode = "200", description = "Lista de incidentes por empleado obtenida exitosamente", content = @Content(schema = @Schema(implementation = IncidenteDTO.class)))
    public List<IncidenteDTO> obtenerIncidentesPorEmpleado(@PathVariable("idEmpleado") Integer idEmpleado) {
        return reporteService.listarIncidentesPorEmpleado(idEmpleado);
    }

    @GetMapping("/kilometros")
    @Operation(summary = "Obtener kilómetros recorridos por vehículo", description = "Devuelve la cantidad de kilómetros recorridos por un vehículo en un rango de fechas.")
    @ApiResponse(responseCode = "200", description = "Kilómetros obtenidos exitosamente", content = @Content(schema = @Schema(implementation = KilometrosDTO.class)))
    public KilometrosDTO obtenerKilometrosVehiculo(
            @RequestParam("idVehiculo") Integer idVehiculo,
            @Parameter(description = "Fecha de inicio (YYYY-MM-DD)", example = "2024-06-01") @RequestParam("desde") String desde,
            @Parameter(description = "Fecha de fin (YYYY-MM-DD)", example = "2024-06-10") @RequestParam("hasta") String hasta) {
        return reporteService.calcularKilometrosRecorridos(idVehiculo, desde, hasta);
    }

    @GetMapping("/pruebas/vehiculo/{vehiculoId}")
    @Operation(summary = "Obtener pruebas por vehículo", description = "Devuelve una lista de pruebas asociadas a un vehículo específico.")
    @ApiResponse(responseCode = "200", description = "Lista de pruebas por vehículo obtenida exitosamente", content = @Content(schema = @Schema(implementation = PruebaDTO.class)))
    public List<PruebaDTO> obtenerPruebasPorVehiculo(@PathVariable("vehiculoId") Integer vehiculoId) {
        return reporteService.listarPruebasPorVehiculo(vehiculoId);
    }
}

package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import serviciousuarios.dtos.IncidenteDTO;
import serviciousuarios.dtos.KilometrosDTO;
import serviciousuarios.dtos.PruebaDTO;
import serviciousuarios.services.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/incidentes")
    public List<IncidenteDTO> obtenerTodosLosIncidentes() {
        return reporteService.listarIncidentes();
    }

    @GetMapping("/incidentes/empleado/{idEmpleado}")
    public List<IncidenteDTO> obtenerIncidentesPorEmpleado(@PathVariable Long idEmpleado) {
        return reporteService.listarIncidentesPorEmpleado(idEmpleado);
    }

    @GetMapping("/kilometros")
    public KilometrosDTO obtenerKilometrosVehiculo(@RequestParam Long idVehiculo,
                                                   @RequestParam String desde,
                                                   @RequestParam String hasta) {
        return reporteService.calcularKilometrosRecorridos(idVehiculo, desde, hasta);
    }

    @GetMapping("/pruebas")
    public List<PruebaDTO> obtenerPruebasPorVehiculo(@RequestParam Long idVehiculo) {
        return reporteService.listarPruebasPorVehiculo(idVehiculo);
    }
}

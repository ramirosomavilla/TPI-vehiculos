package servicioreportes.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import servicioreportes.dtos.IncidenteDTO;
import servicioreportes.dtos.KilometrosDTO;
import servicioreportes.dtos.PruebaDTO;
import servicioreportes.services.ReporteServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {
        private final ReporteServices reporteService;

        public ReporteController(ReporteServices reporteService) {
            this.reporteService = reporteService;
        }

        @GetMapping("/incidentes")
        public List<IncidenteDTO> obtenerTodosLosIncidentes() {
            return reporteService.listarIncidentes();
        }

        @GetMapping("/incidentes/empleado/{idEmpleado}")
        public List<IncidenteDTO> obtenerIncidentesPorEmpleado(@PathVariable("idEmpleado") Integer idEmpleado) {
            return reporteService.listarIncidentesPorEmpleado(idEmpleado);
        }

        @GetMapping("/kilometros")
        public KilometrosDTO obtenerKilometrosVehiculo(
                @RequestParam Long idVehiculo,
                @RequestParam String desde,
                @RequestParam String hasta) {
            return reporteService.calcularKilometrosRecorridos(idVehiculo, desde, hasta);
        }


    @GetMapping("/pruebas/vehiculo/{vehiculoId}")
    public List<PruebaDTO> obtenerPruebasPorVehiculo(@PathVariable Long idVehiculo) {
        return reporteService.listarPruebasPorVehiculo(idVehiculo);
    }
    }

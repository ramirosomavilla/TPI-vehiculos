package servicioreportes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import servicioreportes.client.PruebaClient;
import servicioreportes.dtos.IncidenteDTO;
import servicioreportes.dtos.KilometrosDTO;
import servicioreportes.dtos.PruebaDTO;
import servicioreportes.entities.Posicion;
import servicioreportes.repositories.IncidenteRepository;
import servicioreportes.repositories.PosicionRepository;

    @Service

    public class ReporteServices {

        @Autowired
        private IncidenteRepository incidenteRepo;

        @Autowired
        private PosicionRepository posicionRepo;

        @Autowired
        private PruebaClient pruebaClient;

        // 1. Incidentes desde la tabla
        public List<IncidenteDTO> listarIncidentes() {
            return incidenteRepo.findAll()
                    .stream()
                    .map(IncidenteDTO::toDto)
                    .collect(Collectors.toList());
        }

        public List<IncidenteDTO> listarIncidentesPorEmpleado(int empleadoId) {
            return incidenteRepo.findByEmpleadoId(empleadoId)
                    .stream()
                    .map(IncidenteDTO::toDto)
                    .collect(Collectors.toList());
        }

        // 2. Kilómetros

        public KilometrosDTO calcularKilometrosRecorridos(
                Long vehiculoId, String desde, String hasta) {

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime inicio = LocalDateTime.parse(desde, fmt);
            LocalDateTime fin = LocalDateTime.parse(hasta, fmt);

            List<Posicion> lista = posicionRepo
                    .findByVehiculoIdAndFechas(vehiculoId, inicio, fin);

            double total = 0;
            for (int i = 1; i < lista.size(); i++) {
                total += haversine(lista.get(i - 1), lista.get(i));
            }
            return new KilometrosDTO(vehiculoId, total);
        }

        private double haversine(Posicion p1, Posicion p2) {
            double R = 6378.137; // radio de la tierra en km
            double dLat = Math.toRadians(p2.getLatitud() - p1.getLatitud());
            double dLon = Math.toRadians(p2.getLongitud() - p1.getLongitud());
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(p1.getLatitud()))
                    * Math.cos(Math.toRadians(p2.getLatitud()))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return R * c;
        }

        public List<PruebaDTO> listarPruebasPorVehiculo(Long vehiculoId) {
            return pruebaClient.obtenerPruebasPorVehiculo(vehiculoId);
        }

    }


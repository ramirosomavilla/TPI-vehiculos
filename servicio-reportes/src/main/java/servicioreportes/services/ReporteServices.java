package servicioreportes.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import servicioreportes.client.PruebaClient;
import servicioreportes.dtos.IncidenteDTO;
import servicioreportes.dtos.KilometrosDTO;
import servicioreportes.dtos.PruebaDTO;
import servicioreportes.entities.Incidente;
import servicioreportes.entities.Posicion;
import servicioreportes.repositories.IncidenteRepository;
import servicioreportes.repositories.PosicionRepository;

    @Service

    public class ReporteServices {

        private final IncidenteRepository incidenteRepo;
        private final PosicionRepository posicionRepo;
        private final PruebaClient pruebaClient;

        public ReporteServices(
                IncidenteRepository incidenteRepo,
                PosicionRepository posicionRepo,
                PruebaClient pruebaClient, PruebaClient pruebaClient1) {
            this.incidenteRepo = incidenteRepo;
            this.posicionRepo = posicionRepo;
            //this.pruebaClient = pruebaClient;
            this.pruebaClient = pruebaClient1;
        }

        // 1. Incidentes desde la tabla
        public List<IncidenteDTO> listarIncidentes() {
            return incidenteRepo.findAll()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }

        public List<IncidenteDTO> listarIncidentesPorEmpleado(Long empleadoId) {
            return incidenteRepo.findByEmpleadoId(empleadoId)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }

        private IncidenteDTO toDto(Incidente inc) {
            return new IncidenteDTO(
                    inc.getId(),
                    inc.getEmpleadoId(),
                    inc.getDescripcion(),
                    inc.getFecha().toString()
            );
        }

        // 2. Kilómetros

        public KilometrosDTO calcularKilometrosRecorridos(
                Long vehiculoId, String desde, String hasta) {

            DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
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
            double R = 6378.137; // km
            double dLat = Math.toRadians(p2.getLatitud() - p1.getLatitud());
            double dLon = Math.toRadians(p2.getLongitud() - p1.getLongitud());
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(p1.getLatitud()))
                    * Math.cos(Math.toRadians(p2.getLatitud()))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return R * c;
        }

        private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
            final int R = 6371; // Radio de la Tierra en km
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return R * c;
        }

        public List<PruebaDTO> listarPruebasPorVehiculo(Long vehiculoId) {
            return pruebaClient.obtenerPruebasPorVehiculo(vehiculoId);
        }

    }


package servicioreportes.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import servicioreportes.entities.Posicion;
import servicioreportes.repositories.PosicionRepository;

@Service
public class ReporteKilometrosServices {

        private final PosicionRepository posicionRepository;

        public ReporteKilometrosServices(PosicionRepository posicionRepository) {
            this.posicionRepository = posicionRepository;
        }

        public double calcularKilometrosRecorridos(Long vehiculoId, LocalDateTime inicio, LocalDateTime fin) {
            List<Posicion> posiciones = posicionRepository.findByVehiculoIdAndFechas(vehiculoId, inicio, fin);
            if (posiciones.size() < 2) return 0.0;

            double total = 0.0;
            for (int i = 1; i < posiciones.size(); i++) {
                total += distanciaEuclidea(posiciones.get(i - 1), posiciones.get(i));
            }
            return total;
        }

        private double distanciaEuclidea(Posicion p1, Posicion p2) {
            double dx = p2.getLatitud() - p1.getLatitud();
            double dy = p2.getLongitud() - p1.getLongitud();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }
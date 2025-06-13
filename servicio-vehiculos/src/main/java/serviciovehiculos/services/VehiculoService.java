package serviciovehiculos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfigurationSource;
import serviciovehiculos.client.ConfigClient;
import serviciovehiculos.client.InteresadoClient;
import serviciovehiculos.config.AgencyConfig;
import serviciovehiculos.entities.Vehiculo;
import serviciovehiculos.client.PruebaClient;
import serviciovehiculos.repositories.VehiculoRepository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PruebaClient pruebaClient;

    @Autowired
    private ConfigClient configClient;

    @Autowired
    private InteresadoClient interesadoClient;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> findById(Integer id) {
        return vehiculoRepository.findById(id);
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void deleteById(Integer id) {
        vehiculoRepository.deleteById(id);
    }

    public Vehiculo guardarPosicion(Integer vehiculoId, Double latitud, Double longitud, LocalDateTime timestamp) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
        if (pruebaClient.vehiculoEnPrueba(vehiculoId)) {
            throw new RuntimeException("El vehículo está en prueba");
        }

        AgencyConfig config = configClient.obtenerConfiguracionAgencia();

        boolean fueraDeRadio = !estaDentroDelRadio(latitud, longitud, config);
        boolean enZonaPeligrosa = estaEnZonaPeligrosa(latitud, longitud, config);

        if(fueraDeRadio || enZonaPeligrosa) {
            Integer idInteresado = pruebaClient.obtenerClienteDeVehiculoEnPrueba(vehiculoId);
            interesadoClient.restringirInteresado(idInteresado);
            System.out.println("Cliente " + idInteresado + " restringido por infracción geográfica");
        }

        vehiculo.setLatitud(latitud);
        vehiculo.setLongitud(longitud);
        vehiculo.setFechaUbicacion(timestamp);
        return vehiculoRepository.save(vehiculo);
    }

    private boolean estaDentroDelRadio(Double lat, Double lng, AgencyConfig config) {
        double dx = config.getLatitud() - lat;
        double dy = config.getLongitud() - lng;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia <= config.getRadio();
    }

    private boolean estaEnZonaPeligrosa(Double lat, Double lng, AgencyConfig config) {
        return config.getZonasPeligrosas().stream().anyMatch(zona -> {
            var no = zona.getPuntoNO();
            var se = zona.getPuntoSE();

            return lat <= no.getLat() && lat >= se.getLat()
                    && lng >= no.getLng() && lng <= se.getLng();
        });
    }
} 
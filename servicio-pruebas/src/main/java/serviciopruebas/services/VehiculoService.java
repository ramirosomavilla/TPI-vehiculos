package serviciopruebas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfigurationSource;
import serviciopruebas.client.ConfigClient;
import serviciopruebas.client.InteresadoClient;
import serviciopruebas.config.AgencyConfig;
import serviciopruebas.entities.Vehiculo;
import serviciopruebas.repositories.VehiculoRepository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PruebaService pruebaservice;

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
        if (pruebaservice.vehiculoEnPrueba(vehiculoId)) {
            throw new RuntimeException("El vehículo está en prueba");
        }

        AgencyConfig config = configClient.obtenerConfiguracionAgencia();

        boolean fueraDeRadio = !estaDentroDelRadio(latitud, longitud, config);
        boolean enZonaPeligrosa = estaEnZonaPeligrosa(latitud, longitud, config);

        if(fueraDeRadio || enZonaPeligrosa) {
            Integer idInteresado = pruebaservice.obtenerInteresadoDeVehiculoEnPrueba(vehiculoId);
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
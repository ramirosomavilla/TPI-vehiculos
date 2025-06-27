package serviciopruebas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviciopruebas.client.ConfigClient;
import serviciopruebas.client.InteresadoClient;
import serviciopruebas.client.NotificacionClient;
import serviciopruebas.dtos.AgencyConfigDTO;
import serviciopruebas.dtos.NotificacionRequestDTO;
import serviciopruebas.entities.Posicion;
import serviciopruebas.entities.Prueba;
import serviciopruebas.entities.Vehiculo;
import serviciopruebas.repositories.PosicionRepository;
import serviciopruebas.repositories.VehiculoRepository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PosicionRepository posicionRepository;

    @Autowired
    private PruebaService pruebaservice;

    @Autowired
    private ConfigClient configClient;

    @Autowired
    private InteresadoClient interesadoClient;

    @Autowired
    private NotificacionClient notificacionClient;

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> findById(Integer id) {
        return vehiculoRepository.findById(id);
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.saveAndFlush(vehiculo);
    }

    public void deleteById(Integer id) {
        vehiculoRepository.deleteById(id);
    }

    public Vehiculo guardarPosicion(Integer vehiculoId, Double latitud, Double longitud, LocalDateTime timestamp) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
        if (!pruebaservice.vehiculoEnPrueba(vehiculoId)) {
            throw new RuntimeException("El vehículo no está en prueba");
        }

        AgencyConfigDTO config = configClient.obtenerConfiguracionAgencia();

        System.out.println("Configuración de agencia: " + config);

        boolean fueraDeRadio = !estaDentroDelRadio(latitud, longitud, config);
        boolean enZonaPeligrosa = estaEnZonaPeligrosa(latitud, longitud, config);

        if (fueraDeRadio || enZonaPeligrosa) {
            Prueba pruebaEnCurso = pruebaservice.obtenerPruebaEnCursoByVehiculoId(vehiculoId);
            interesadoClient.restringirInteresado(pruebaEnCurso.getIdInteresado());

            NotificacionRequestDTO notificacionRequest = new NotificacionRequestDTO(
                    pruebaEnCurso.getIdEmpleado(),
                    pruebaEnCurso.getIdVehiculo(),
                    pruebaEnCurso.getIdInteresado(),
                    "Restricción Geográfica",
                    "El vehículo ha sido ubicado fuera del radio permitido o en una zona peligrosa. El interesado ha sido restringido.");
            notificacionClient.notificarInteresado(notificacionRequest);

            System.out.println("Cliente " + pruebaEnCurso.getIdInteresado() + " restringido por infracción geográfica");
        }

        Posicion posicion = new Posicion();
        posicion.setIdVehiculo(vehiculoId);
        posicion.setLatitud(latitud);
        posicion.setLongitud(longitud);
        posicion.setFechaHora(timestamp);
        posicionRepository.save(posicion);

        vehiculo.setLatitud(latitud);
        vehiculo.setLongitud(longitud);
        vehiculo.setFechaUbicacion(timestamp);
        return vehiculoRepository.save(vehiculo);
    }

    private boolean estaDentroDelRadio(Double lat, Double lng, AgencyConfigDTO config) {
        AgencyConfigDTO.Ubicacion centro = config.getUbicacionAgencia();
        double dx = centro.getLatitud() - lat;
        double dy = centro.getLongitud() - lng;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia <= config.getRadioMaximoMetros();
    }

    private boolean estaEnZonaPeligrosa(Double lat, Double lng, AgencyConfigDTO config) {
        return config.getZonasPeligrosas().stream().anyMatch(zona -> {
            double dx = zona.getCoordenadas().getLatitud() - lat;
            double dy = zona.getCoordenadas().getLongitud() - lng;
            double distancia = Math.sqrt(dx * dx + dy * dy);
            return distancia <= zona.getRadioMetros();
        });
    }
}
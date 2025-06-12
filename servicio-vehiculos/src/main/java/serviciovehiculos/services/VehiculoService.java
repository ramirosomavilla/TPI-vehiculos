package serviciovehiculos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        vehiculo.setLatitud(latitud);
        vehiculo.setLongitud(longitud);
        vehiculo.setFechaUbicacion(timestamp);
        return vehiculoRepository.save(vehiculo);
    }
} 
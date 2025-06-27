package serviciopruebas.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviciopruebas.repositories.PruebaRepository;
import serviciopruebas.entities.Prueba;
import serviciopruebas.dtos.PruebaDTO;
import serviciopruebas.client.UsuarioClient;

@Service
public class PruebaService {
  @Autowired
  private PruebaRepository pruebaRepository;

  @Autowired
  private UsuarioClient usuarioClient;

  public PruebaDTO create(PruebaDTO pruebaDTO) {
    Prueba prueba = pruebaDTO.toEntity();

    if (usuarioClient.interesadoHasExpiredLicense(prueba.getIdInteresado())) {
      throw new RuntimeException("El usuario tiene una licencia expirada");
    }

    if (usuarioClient.interesadoIsRestricted(prueba.getIdInteresado())) {
      throw new RuntimeException("El usuario está restringido");
    }

    if (pruebaRepository.existsByIdVehiculoAndFechaHoraFinIsNull(prueba.getIdVehiculo())) {
      throw new RuntimeException("El vehículo ya está siendo utilizado en otra prueba");
    }

    prueba = pruebaRepository.save(prueba);
    return prueba.toDTO();
  }

  public List<Prueba> findAll() {
    return pruebaRepository.findAll();
  }

  public List<PruebaDTO> getPruebasEnCurso() {
    List<Prueba> pruebas = pruebaRepository.findByFechaHoraFinIsNull();
    return pruebas.stream().map(Prueba::toDTO).collect(Collectors.toList());
  }

  public PruebaDTO finalizar(Integer id, String comentarios) {
    Prueba prueba = pruebaRepository.findById(id).orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
    if (prueba.getFechaHoraFin() != null) {
      throw new RuntimeException("La prueba ya ha sido finalizada");
    }
    prueba.setFechaHoraFin(LocalDateTime.now());
    prueba.setComentarios(comentarios);
    prueba = pruebaRepository.save(prueba);
    return prueba.toDTO();
  }

  public Boolean vehiculoEnPrueba(Integer idVehiculo) {
    return pruebaRepository.existsByIdVehiculoAndFechaHoraFinIsNull(idVehiculo);
  }

  public Prueba obtenerPruebaEnCursoByVehiculoId(Integer idVehiculo) {
    return pruebaRepository.findByIdVehiculoAndFechaHoraFinIsNull(idVehiculo)
        .orElseThrow(() -> new RuntimeException("No hay prueba en curso para el vehículo con ID: " + idVehiculo));
  }

  public List<PruebaDTO> pruebasPorVehiculo(Integer idVehiculo) {
    List<Prueba> pruebas = pruebaRepository.findByIdVehiculo(idVehiculo);
    return pruebas.stream().map(Prueba::toDTO).collect(Collectors.toList());
  }

  public void deleteById(Integer id) {
    pruebaRepository.deleteById(id);
  }

  public Prueba update(Integer id, PruebaDTO dto) {
    Prueba prueba = pruebaRepository.findById(id).orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
    prueba.setIdVehiculo(dto.getIdVehiculo());
    prueba.setIdInteresado(dto.getIdInteresado());
    prueba.setIdEmpleado(dto.getIdEmpleado());
    prueba.setFechaHoraInicio(dto.getFechaHoraInicio());
    prueba.setFechaHoraFin(dto.getFechaHoraFin());
    prueba.setComentarios(dto.getComentarios());
    return pruebaRepository.save(prueba);
  }

  public Prueba partialUpdate(Integer id, PruebaDTO dto) {
    Prueba prueba = pruebaRepository.findById(id).orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
    if (dto.getIdVehiculo() != null)
      prueba.setIdVehiculo(dto.getIdVehiculo());
    if (dto.getIdInteresado() != null)
      prueba.setIdInteresado(dto.getIdInteresado());
    if (dto.getIdEmpleado() != null)
      prueba.setIdEmpleado(dto.getIdEmpleado());
    if (dto.getFechaHoraInicio() != null)
      prueba.setFechaHoraInicio(dto.getFechaHoraInicio());
    if (dto.getFechaHoraFin() != null)
      prueba.setFechaHoraFin(dto.getFechaHoraFin());
    if (dto.getComentarios() != null)
      prueba.setComentarios(dto.getComentarios());
    return pruebaRepository.save(prueba);
  }
}

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

  @Autowired
  private NotificacionService notificacionService;


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

    //Lo hice para que se haga una promocion cada vez que se inicia una prueba

    String mensaje = "¡¡¡Nueva Promocion!!! Vehículo: " + prueba.getIdVehiculo() +
            " - Interesado: " + prueba.getIdInteresado();
    notificacionService.crearNotificacionPromocion(
            prueba.getIdCliente(),
            prueba.getIdVehiculo(),
            prueba.getIdInteresado(),
            mensaje

    prueba = pruebaRepository.save(prueba);
    return prueba.toDTO();
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
}

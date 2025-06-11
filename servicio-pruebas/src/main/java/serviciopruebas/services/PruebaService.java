package serviciopruebas.services;

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

  public List<PruebaDTO> getPruebasEnCurso() {
    List<Prueba> pruebas = pruebaRepository.findByFechaHoraFinIsNull();
    return pruebas.stream().map(Prueba::toDTO).collect(Collectors.toList());
  }
}

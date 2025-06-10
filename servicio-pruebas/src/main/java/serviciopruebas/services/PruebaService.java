package serviciopruebas.services;

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
    prueba = pruebaRepository.save(prueba);

    if (usuarioClient.userHasExpiredLicense(prueba.getIdInteresado())) {
      throw new RuntimeException("El usuario tiene una licencia expirada");
    }
    
    return prueba.toDTO();
  }
}

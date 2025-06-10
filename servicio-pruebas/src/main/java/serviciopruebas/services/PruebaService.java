package serviciopruebas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviciopruebas.repositories.PruebaRepository;
import serviciopruebas.entities.Prueba;
import serviciopruebas.dtos.PruebaDTO;

@Service
public class PruebaService {
  @Autowired
  private PruebaRepository pruebaRepository;

  public PruebaDTO create(PruebaDTO pruebaDTO) {
    Prueba prueba = pruebaDTO.toEntity();
    prueba = pruebaRepository.save(prueba);
    return prueba.toDTO();
  }
}

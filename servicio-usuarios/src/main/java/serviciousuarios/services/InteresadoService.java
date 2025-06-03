package serviciousuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import serviciousuarios.dtos.InteresadoDTO;
import serviciousuarios.entities.Interesado;
import serviciousuarios.repositories.InteresadoRepository;

@Service
public class InteresadoService {
  @Autowired
  private InteresadoRepository interesadoRepository;

  public List<InteresadoDTO> getAllInteresados() {
    List<Interesado> interesados = interesadoRepository.findAll();
    return interesados.stream().map(Interesado::ToDTO).collect(Collectors.toList());
  }
}

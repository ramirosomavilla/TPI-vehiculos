package serviciousuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import serviciousuarios.dtos.InteresadoDTO;

import java.util.List;
import java.util.stream.Collectors;

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

  public boolean hasExpiredLicense(int idUsuario) {
    Interesado interesado = interesadoRepository.findById((long) idUsuario).orElse(null);
    if (interesado == null) {
      throw new RuntimeException("Interesado no encontrado");
    }
    String fechaVencimiento = interesado.getFechaVencimientoLicencia();
    if (fechaVencimiento == null || fechaVencimiento.isEmpty()) {
      return true; // Si no hay fecha, se considera expirada
    }
    java.time.LocalDate fechaVenc;
    try {
      fechaVenc = java.time.LocalDate.parse(fechaVencimiento);
    } catch (Exception e) {
      return true; // Si el formato es inválido, se considera expirada
    }
    return fechaVenc.isBefore(java.time.LocalDate.now());
  }

  public boolean isRestricted(int idUsuario) {
    Interesado interesado = interesadoRepository.findById((long) idUsuario).orElse(null);
    if (interesado == null) {
      throw new RuntimeException("Interesado no encontrado");
    }
    return interesado.getRestringido() == 1;
  }

  public void restringir(Long idUsuario) {
    Interesado interesado = interesadoRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Interesado no encontrado"));
    interesado.setRestringido(1);
    interesadoRepository.save(interesado);
  }

}

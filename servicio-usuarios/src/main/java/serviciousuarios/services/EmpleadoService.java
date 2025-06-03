package serviciousuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import serviciousuarios.dtos.EmpleadoDTO;
import serviciousuarios.entities.Empleado;
import serviciousuarios.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {
  @Autowired
  private EmpleadoRepository empleadoRepository;

  public List<EmpleadoDTO> getAllEmpleados() {
    List<Empleado> empleados = empleadoRepository.findAll();
    return empleados.stream().map(Empleado::toDTO).collect(Collectors.toList());
  }
}

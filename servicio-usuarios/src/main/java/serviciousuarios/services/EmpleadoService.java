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

  public EmpleadoDTO createEmpleado(EmpleadoDTO empleadoDTO) {
    if (empleadoDTO == null) {
      throw new IllegalArgumentException("EmpleadoDTO no puede ser null");
    }

    if (empleadoDTO.getNombre() == null || empleadoDTO.getApellido() == null) {
      throw new IllegalArgumentException("Nombre y apellido son campos requeridos");
    }
    Empleado nuevoEmpleado = new Empleado();
    nuevoEmpleado.setNombre(empleadoDTO.getNombre());
    nuevoEmpleado.setApellido(empleadoDTO.getApellido());
    nuevoEmpleado.setTelefonoContacto(empleadoDTO.getTelefonoContacto());

    Empleado savedEmpleado = empleadoRepository.save(nuevoEmpleado);
    return savedEmpleado.toDTO();
  }
}

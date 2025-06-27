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

  public EmpleadoDTO getEmpleadoById(int legajo) {
    return empleadoRepository.findById((long) legajo).map(Empleado::toDTO).orElse(null);
  }

  public EmpleadoDTO updateEmpleado(int legajo, EmpleadoDTO empleadoDTO) {
    return empleadoRepository.findById((long) legajo).map(empleado -> {
      empleado.setNombre(empleadoDTO.getNombre());
      empleado.setApellido(empleadoDTO.getApellido());
      empleado.setTelefonoContacto(empleadoDTO.getTelefonoContacto());
      Empleado updated = empleadoRepository.save(empleado);
      return updated.toDTO();
    }).orElse(null);
  }

  public EmpleadoDTO patchEmpleado(int legajo, EmpleadoDTO empleadoDTO) {
    return empleadoRepository.findById((long) legajo).map(empleado -> {
      if (empleadoDTO.getNombre() != null)
        empleado.setNombre(empleadoDTO.getNombre());
      if (empleadoDTO.getApellido() != null)
        empleado.setApellido(empleadoDTO.getApellido());
      if (empleadoDTO.getTelefonoContacto() != null)
        empleado.setTelefonoContacto(empleadoDTO.getTelefonoContacto());
      Empleado updated = empleadoRepository.save(empleado);
      return updated.toDTO();
    }).orElse(null);
  }

  public boolean deleteEmpleado(int legajo) {
    if (!empleadoRepository.existsById((long) legajo))
      return false;
    empleadoRepository.deleteById((long) legajo);
    return true;
  }
}

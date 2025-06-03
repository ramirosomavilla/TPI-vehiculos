package serviciousuarios.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import lombok.Data;
import serviciousuarios.dtos.EmpleadoDTO;

@Entity
@Table(name = "Empleados")
@Data
public class Empleado {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "legajo")
  private int legajo;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "telefono_contacto")
  private String telefonoContacto;

  public EmpleadoDTO toDTO() {
    EmpleadoDTO dto = new EmpleadoDTO();
    dto.setLegajo(this.legajo);
    dto.setNombre(this.nombre);
    dto.setApellido(this.apellido);
    dto.setTelefonoContacto(this.telefonoContacto);
    return dto;
  }
}

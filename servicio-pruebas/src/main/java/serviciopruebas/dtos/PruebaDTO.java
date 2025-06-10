package serviciopruebas.dtos;

import java.time.LocalDateTime;

import lombok.Data;
import serviciopruebas.entities.Prueba;

@Data
public class PruebaDTO {
  private Integer id;
  private Integer idVehiculo;
  private Integer idInteresado;
  private Integer idEmpleado;
  private LocalDateTime fechaHoraInicio;
  private LocalDateTime fechaHoraFin;
  private String comentarios;

  public Prueba toEntity() {
    Prueba prueba = new Prueba();
    prueba.setIdVehiculo(this.getIdVehiculo());
    prueba.setIdInteresado(this.getIdInteresado());
    prueba.setIdEmpleado(this.getIdEmpleado());
    prueba.setFechaHoraInicio(this.getFechaHoraInicio());
    prueba.setFechaHoraFin(this.getFechaHoraFin());
    prueba.setComentarios(this.getComentarios());
    return prueba;
  }
}

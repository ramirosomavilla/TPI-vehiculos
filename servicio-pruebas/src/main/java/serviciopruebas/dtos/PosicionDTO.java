package serviciopruebas.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PosicionDTO {
  private Integer idVehiculo;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fechaHora;
}
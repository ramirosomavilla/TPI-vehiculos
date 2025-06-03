package serviciousuarios.dtos;

import lombok.Data;

@Data
public class InteresadoDTO {
  private int id;
  private String tipoDocumento;
  private String documento;
  private String nombre;
  private String apellido;
  private int restringido;
  private int nroLicencia;
  private String fechaVencimientoLicencia;
}

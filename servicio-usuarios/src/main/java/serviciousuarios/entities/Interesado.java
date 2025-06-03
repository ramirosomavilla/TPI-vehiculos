package serviciousuarios.entities;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import serviciousuarios.dtos.InteresadoDTO;

@Entity
@Table(name = "Interesados")
@Data
public class Interesado {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "tipo_documento")
  private String tipoDocumento;

  @Column(name = "documento")
  private String documento;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "restringido")
  private int restringido;

  @Column(name = "nro_licencia")
  private int nroLicencia;

  @Column(name = "fecha_vencimiento_licencia")
  private String fechaVencimientoLicencia;

  public InteresadoDTO ToDTO() {
    InteresadoDTO interesadoDTO = new InteresadoDTO();
    interesadoDTO.setId(id);
    interesadoDTO.setTipoDocumento(tipoDocumento);
    interesadoDTO.setDocumento(documento);
    interesadoDTO.setNombre(nombre);
    interesadoDTO.setApellido(apellido);
    interesadoDTO.setRestringido(restringido);
    interesadoDTO.setNroLicencia(nroLicencia);
    interesadoDTO.setFechaVencimientoLicencia(fechaVencimientoLicencia);
    return interesadoDTO;
  }
}

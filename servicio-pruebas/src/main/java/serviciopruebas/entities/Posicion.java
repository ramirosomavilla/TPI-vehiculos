package serviciopruebas.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Data;

@Entity
@Table(name = "Posiciones")
@Data
public class Posicion {
  @Id
  @GeneratedValue(generator = "posiciones")
  @TableGenerator(name = "posiciones", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "Posiciones", initialValue = 1, allocationSize = 1)
  @Column(name = "id")
  private int id;

  @Column(name = "id_vehiculo")
  private int idVehiculo;

  @Column(name = "latitud")
  private double latitud;

  @Column(name = "longitud")
  private double longitud;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;
}

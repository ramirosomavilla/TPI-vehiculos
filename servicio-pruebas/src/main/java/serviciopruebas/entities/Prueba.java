package serviciopruebas.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Data;
import serviciopruebas.dtos.PruebaDTO;

@Entity
@Table(name = "Pruebas")
@Data
public class Prueba {
    @Id
    @GeneratedValue(generator = "pruebas")
    @TableGenerator(name = "pruebas", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "Pruebas", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "id_vehiculo")
    private int idVehiculo;

    @Column(name = "id_interesado")
    private int idInteresado;

    @Column(name = "id_empleado")
    private int idEmpleado;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @Column(name = "comentarios")
    private String comentarios;

    public PruebaDTO toDTO() {
        PruebaDTO pruebaDTO = new PruebaDTO();
        pruebaDTO.setId(this.id);
        pruebaDTO.setIdVehiculo(this.getIdVehiculo());
        pruebaDTO.setIdInteresado(this.getIdInteresado());
        pruebaDTO.setIdEmpleado(this.getIdEmpleado());
        pruebaDTO.setFechaHoraInicio(this.getFechaHoraInicio());
        pruebaDTO.setFechaHoraFin(this.getFechaHoraFin());
        pruebaDTO.setComentarios(this.getComentarios());
        return pruebaDTO;
    }
}

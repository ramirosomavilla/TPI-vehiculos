package servicioreportes.dtos;

import lombok.Data;
import servicioreportes.entities.Empleado;

@Data
public class EmpleadoDTO {
        private int legajo;
        private String nombre;
        private String apellido;
        private String telefonoContacto;

        public Empleado ToEntity() {
            Empleado empleado = new Empleado();
            empleado.setLegajo(legajo);
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setTelefonoContacto(telefonoContacto);
            return empleado;
        }
    }

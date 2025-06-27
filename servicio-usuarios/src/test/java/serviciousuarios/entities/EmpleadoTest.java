package serviciousuarios.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import serviciousuarios.dtos.EmpleadoDTO;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test para la entidad Empleado")
public class EmpleadoTest {

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado();
    }

    @Nested
    @DisplayName("Tests de creación y asignación de propiedades")
    class CreacionYAsignacionTest {

        @Test
        @DisplayName("Debería crear una instancia de Empleado con valores por defecto")
        void deberiaCrearInstanciaEmpleado() {
            // Given & When
            Empleado nuevoEmpleado = new Empleado();

            // Then
            assertThat(nuevoEmpleado).isNotNull();
            assertThat(nuevoEmpleado.getLegajo()).isEqualTo(0); // Valor por defecto de un int
            assertThat(nuevoEmpleado.getNombre()).isNull();
            assertThat(nuevoEmpleado.getApellido()).isNull();
            assertThat(nuevoEmpleado.getTelefonoContacto()).isNull();
        }

        @Test
        @DisplayName("Debería asignar y obtener legajo correctamente")
        void deberiaAsignarYObtenerLegajoCorrectamente() {
            // Given
            int legajoEsperado = 98765;

            // When
            empleado.setLegajo(legajoEsperado);

            // Then
            assertThat(empleado.getLegajo()).isEqualTo(legajoEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener nombre correctamente")
        void deberiaAsignarYObtenerNombreCorrectamente() {
            // Given
            String nombreEsperado = "Lionel";

            // When
            empleado.setNombre(nombreEsperado);

            // Then
            assertThat(empleado.getNombre()).isEqualTo(nombreEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener apellido correctamente")
        void deberiaAsignarYObtenerApellidoCorrectamente() {
            // Given
            String apellidoEsperado = "Messi";

            // When
            empleado.setApellido(apellidoEsperado);

            // Then
            assertThat(empleado.getApellido()).isEqualTo(apellidoEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener teléfono de contacto correctamente")
        void deberiaAsignarYObtenerTelefonoContactoCorrectamente() {
            // Given
            String telefonoEsperado = "10101010";

            // When
            empleado.setTelefonoContacto(telefonoEsperado);

            // Then
            assertThat(empleado.getTelefonoContacto()).isEqualTo(telefonoEsperado);
        }
    }

    @Nested
    @DisplayName("Tests del método toDTO")
    class ToDTOTest {

        @Test
        @DisplayName("Debería convertir entidad completa a DTO correctamente")
        void deberiaConvertirEntidadCompletaADTO() {
            // Given
            empleado.setLegajo(10);
            empleado.setNombre("Julián");
            empleado.setApellido("Álvarez");
            empleado.setTelefonoContacto("999999");

            // When
            EmpleadoDTO dto = empleado.toDTO();

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getLegajo()).isEqualTo(10);
            assertThat(dto.getNombre()).isEqualTo("Julián");
            assertThat(dto.getApellido()).isEqualTo("Álvarez");
            assertThat(dto.getTelefonoContacto()).isEqualTo("999999");
        }

        @Test
        @DisplayName("Debería convertir entidad con valores nulos a DTO")
        void deberiaConvertirEntidadConValoresNulosADTO() {
            // Given
            empleado.setLegajo(7);
            // El resto de los campos son nulos por defecto

            // When
            EmpleadoDTO dto = empleado.toDTO();

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getLegajo()).isEqualTo(7);
            assertThat(dto.getNombre()).isNull();
            assertThat(dto.getApellido()).isNull();
            assertThat(dto.getTelefonoContacto()).isNull();
        }

        @Test
        @DisplayName("Debería crear una instancia diferente de DTO en cada llamada")
        void deberiaCrearInstanciaDiferenteDTO() {
            // Given
            empleado.setLegajo(1);

            // When
            EmpleadoDTO dto1 = empleado.toDTO();
            EmpleadoDTO dto2 = empleado.toDTO();

            // Then: Comprueba que no son el mismo objeto en memoria
            assertThat(dto1).isNotSameAs(dto2);
            // Pero comprueba que sus contenidos son iguales
            assertThat(dto1).isEqualTo(dto2);
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode (Lombok)")
    class EqualsHashCodeTest {

        // Método helper para no repetir código y hacer los tests más limpios
        private void configurarEmpleado(Empleado emp) {
            emp.setLegajo(24);
            emp.setNombre("Enzo");
            emp.setApellido("Fernández");
            emp.setTelefonoContacto("555-1234");
        }

        @Test
        @DisplayName("Debería ser igual a sí misma")
        void deberiaSerIgualASiMisma() {
            // Given
            configurarEmpleado(empleado);

            // Then
            assertThat(empleado).isEqualTo(empleado);
            assertThat(empleado.hashCode()).isEqualTo(empleado.hashCode());
        }

        @Test
        @DisplayName("Debería ser igual a otra instancia con los mismos datos")
        void deberiaSerIgualAOtraInstanciaConMismosDatos() {
            // Given
            configurarEmpleado(empleado);

            Empleado otraInstancia = new Empleado();
            configurarEmpleado(otraInstancia);

            // Then
            assertThat(empleado).isEqualTo(otraInstancia);
            assertThat(empleado.hashCode()).isEqualTo(otraInstancia.hashCode());
        }

        @Test
        @DisplayName("No debería ser igual a null")
        void noDeberiaSerIgualANull() {
            // Given
            configurarEmpleado(empleado);

            // Then
            assertThat(empleado).isNotEqualTo(null);
        }

        @Test
        @DisplayName("No debería ser igual a un objeto de diferente clase")
        void noDeberiaSerIgualAObjetoDiferenteClase() {
            // Given
            configurarEmpleado(empleado);
            String otroObjeto = "Esto no es un empleado";

            // Then
            assertThat(empleado).isNotEqualTo(otroObjeto);
        }
    }
}
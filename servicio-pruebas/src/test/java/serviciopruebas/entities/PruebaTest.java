package serviciopruebas.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import serviciopruebas.dtos.PruebaDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Test para entidad prueba")
public class PruebaTest {
    private Prueba prueba;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @BeforeEach
    void setUp() {
        prueba = new Prueba();
        fechaInicio = LocalDateTime.of(2024, 6, 26, 10, 0);
        fechaFin = LocalDateTime.of(2024, 6, 26, 11, 30);
    }

    @Nested
    @DisplayName("Tests de creación y asignación de propiedades")
    class CreacionYAsignacionTest {

        @Test
        @DisplayName("Debería crear una instancia de Prueba")
        void deberiaCrearInstanciaPrueba() {
            // Given & When
            Prueba nuevaPrueba = new Prueba();

            // Then
            assertThat(nuevaPrueba).isNotNull();
            assertThat(nuevaPrueba.getId()).isEqualTo(0); // int default value
            assertThat(nuevaPrueba.getIdVehiculo()).isEqualTo(0);
            assertThat(nuevaPrueba.getIdInteresado()).isEqualTo(0);
            assertThat(nuevaPrueba.getIdEmpleado()).isEqualTo(0);
            assertThat(nuevaPrueba.getFechaHoraInicio()).isNull();
            assertThat(nuevaPrueba.getFechaHoraFin()).isNull();
            assertThat(nuevaPrueba.getComentarios()).isNull();
        }

        @Test
        @DisplayName("Debería asignar y obtener ID correctamente")
        void deberiaAsignarYObtenerIdCorrectamente() {
            // Given
            int idEsperado = 123;

            // When
            prueba.setId(idEsperado);

            // Then
            assertThat(prueba.getId()).isEqualTo(idEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener ID de vehículo correctamente")
        void deberiaAsignarYObtenerIdVehiculoCorrectamente() {
            // Given
            int idVehiculoEsperado = 456;

            // When
            prueba.setIdVehiculo(idVehiculoEsperado);

            // Then
            assertThat(prueba.getIdVehiculo()).isEqualTo(idVehiculoEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener ID de interesado correctamente")
        void deberiaAsignarYObtenerIdInteresadoCorrectamente() {
            // Given
            int idInteresadoEsperado = 789;

            // When
            prueba.setIdInteresado(idInteresadoEsperado);

            // Then
            assertThat(prueba.getIdInteresado()).isEqualTo(idInteresadoEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener ID de empleado correctamente")
        void deberiaAsignarYObtenerIdEmpleadoCorrectamente() {
            // Given
            int idEmpleadoEsperado = 321;

            // When
            prueba.setIdEmpleado(idEmpleadoEsperado);

            // Then
            assertThat(prueba.getIdEmpleado()).isEqualTo(idEmpleadoEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener fecha de inicio correctamente")
        void deberiaAsignarYObtenerFechaInicioCorrectamente() {
            // When
            prueba.setFechaHoraInicio(fechaInicio);

            // Then
            assertThat(prueba.getFechaHoraInicio()).isEqualTo(fechaInicio);
        }

        @Test
        @DisplayName("Debería asignar y obtener fecha de fin correctamente")
        void deberiaAsignarYObtenerFechaFinCorrectamente() {
            // When
            prueba.setFechaHoraFin(fechaFin);

            // Then
            assertThat(prueba.getFechaHoraFin()).isEqualTo(fechaFin);
        }

        @Test
        @DisplayName("Debería asignar y obtener comentarios correctamente")
        void deberiaAsignarYObtenerComentariosCorrectamente() {
            // Given
            String comentariosEsperados = "Prueba realizada sin inconvenientes";

            // When
            prueba.setComentarios(comentariosEsperados);

            // Then
            assertThat(prueba.getComentarios()).isEqualTo(comentariosEsperados);
        }

        @Test
        @DisplayName("Debería manejar comentarios nulos")
        void deberiaManejarComentariosNulos() {
            // When
            prueba.setComentarios(null);

            // Then
            assertThat(prueba.getComentarios()).isNull();
        }

        @Test
        @DisplayName("Debería manejar comentarios vacíos")
        void deberiaManejarComentariosVacios() {
            // Given
            String comentariosVacios = "";

            // When
            prueba.setComentarios(comentariosVacios);

            // Then
            assertThat(prueba.getComentarios()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Tests del método toDTO")
    class ToDTOTest {

        @Test
        @DisplayName("Debería convertir entidad completa a DTO correctamente")
        void deberiaConvertirEntidadCompletaADTO() {
            // Given
            prueba.setId(1);
            prueba.setIdVehiculo(100);
            prueba.setIdInteresado(200);
            prueba.setIdEmpleado(300);
            prueba.setFechaHoraInicio(fechaInicio);
            prueba.setFechaHoraFin(fechaFin);
            prueba.setComentarios("Prueba exitosa");

            // When
            PruebaDTO dto = prueba.toDTO();

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(1);
            assertThat(dto.getIdVehiculo()).isEqualTo(100);
            assertThat(dto.getIdInteresado()).isEqualTo(200);
            assertThat(dto.getIdEmpleado()).isEqualTo(300);
            assertThat(dto.getFechaHoraInicio()).isEqualTo(fechaInicio);
            assertThat(dto.getFechaHoraFin()).isEqualTo(fechaFin);
            assertThat(dto.getComentarios()).isEqualTo("Prueba exitosa");
        }

        @Test
        @DisplayName("Debería convertir entidad con valores nulos a DTO")
        void deberiaConvertirEntidadConValoresNulosADTO() {
            // Given
            prueba.setId(2);
            prueba.setIdVehiculo(0);
            prueba.setIdInteresado(0);
            prueba.setIdEmpleado(0);
            // fechas y comentarios quedan null

            // When
            PruebaDTO dto = prueba.toDTO();

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(2);
            assertThat(dto.getIdVehiculo()).isEqualTo(0);
            assertThat(dto.getIdInteresado()).isEqualTo(0);
            assertThat(dto.getIdEmpleado()).isEqualTo(0);
            assertThat(dto.getFechaHoraInicio()).isNull();
            assertThat(dto.getFechaHoraFin()).isNull();
            assertThat(dto.getComentarios()).isNull();
        }

        @Test
        @DisplayName("Debería crear instancia diferente de DTO")
        void deberiaCrearInstanciaDiferenteDTO() {
            // Given
            prueba.setId(3);

            // When
            PruebaDTO dto1 = prueba.toDTO();
            PruebaDTO dto2 = prueba.toDTO();

            // Then
            assertThat(dto1).isNotSameAs(dto2);
            assertThat(dto1.getId()).isEqualTo(dto2.getId());
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode (Lombok)")
    class EqualsHashCodeTest {

        @Test
        @DisplayName("Debería ser igual a sí misma")
        void deberiaSerIgualASiMisma() {
            // Given
            configurarPrueba(prueba);

            // Then
            assertThat(prueba).isEqualTo(prueba);
            assertThat(prueba.hashCode()).isEqualTo(prueba.hashCode());
        }

        @Test
        @DisplayName("Debería ser igual a otra instancia con mismos datos")
        void deberiaSerIgualAOtraInstanciaConMismosDatos() {
            // Given
            configurarPrueba(prueba);

            Prueba otraPrueba = new Prueba();
            configurarPrueba(otraPrueba);

            // Then
            assertThat(prueba).isEqualTo(otraPrueba);
            assertThat(prueba.hashCode()).isEqualTo(otraPrueba.hashCode());
        }

        @Test
        @DisplayName("No debería ser igual a null")
        void noDeberiaSerIgualANull() {
            // Given
            configurarPrueba(prueba);

            // Then
            assertThat(prueba).isNotEqualTo(null);
        }

        @Test
        @DisplayName("No debería ser igual a objeto de diferente clase")
        void noDeberiaSerIgualAObjetoDiferenteClase() {
            // Given
            configurarPrueba(prueba);
            String otroObjeto = "No soy una prueba";

            // Then
            assertThat(prueba).isNotEqualTo(otroObjeto);
        }

        private void configurarPrueba(Prueba prueba) {
            prueba.setId(1);
            prueba.setIdVehiculo(100);
            prueba.setIdInteresado(200);
            prueba.setIdEmpleado(300);
            prueba.setFechaHoraInicio(fechaInicio);
            prueba.setFechaHoraFin(fechaFin);
            prueba.setComentarios("Comentario de prueba");
        }
    }
}

package serviciousuarios.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DisplayName("Test para la entidad Notificacion")
public class NotificacionTest {

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
    }

    @Nested
    @DisplayName("Tests de constructores y asignación de propiedades")
    class ConstructoresYAsignacionTest {

        @Test
        @DisplayName("Debería crear una instancia con el constructor por defecto")
        void deberiaCrearInstanciaConConstructorPorDefecto() {
            // Given & When
            Notificacion nuevaNotificacion = new Notificacion();

            // Then
            assertThat(nuevaNotificacion).isNotNull();
            assertThat(nuevaNotificacion.getId()).isNull();
            assertThat(nuevaNotificacion.getIdEmpleado()).isNull();
            assertThat(nuevaNotificacion.getIdVehiculo()).isNull();
            assertThat(nuevaNotificacion.getIdInteresado()).isNull();
            assertThat(nuevaNotificacion.getTipo()).isNull();
            assertThat(nuevaNotificacion.getMensaje()).isNull();
            assertThat(nuevaNotificacion.getFechaCreacion()).isNull();
            assertThat(nuevaNotificacion.isLeida()).isFalse(); // Valor por defecto de boolean
        }

        @Test
        @DisplayName("Debería crear una instancia con el constructor parametrizado correctamente")
        void deberiaCrearInstanciaConConstructorParametrizado() {
            // Given
            Integer idEmpleado = 10;
            Integer idVehiculo = 20;
            Integer idInteresado = 30;
            String tipo = "PROMOCION";
            String mensaje = "Oferta especial para usted";
            LocalDateTime antesDeCrear = LocalDateTime.now();

            // When
            Notificacion nuevaNotificacion = new Notificacion(idEmpleado, idVehiculo, idInteresado, tipo, mensaje);

            // Then
            assertThat(nuevaNotificacion).isNotNull();
            assertThat(nuevaNotificacion.getIdEmpleado()).isEqualTo(idEmpleado);
            assertThat(nuevaNotificacion.getIdVehiculo()).isEqualTo(idVehiculo);
            assertThat(nuevaNotificacion.getIdInteresado()).isEqualTo(idInteresado);
            assertThat(nuevaNotificacion.getTipo()).isEqualTo(tipo);
            assertThat(nuevaNotificacion.getMensaje()).isEqualTo(mensaje);

            // Verificamos la lógica interna del constructor
            assertThat(nuevaNotificacion.isLeida()).isFalse();
            assertThat(nuevaNotificacion.getFechaCreacion()).isNotNull();
            assertThat(nuevaNotificacion.getFechaCreacion()).isCloseTo(antesDeCrear, within(1, ChronoUnit.SECONDS));
        }

        @Test
        @DisplayName("Debería asignar y obtener todas las propiedades correctamente con setters")
        void deberiaAsignarYObtenerPropiedades() {
            // Given
            LocalDateTime fecha = LocalDateTime.of(2024, 1, 1, 12, 0);

            // When
            notificacion.setId(1);
            notificacion.setIdEmpleado(101);
            notificacion.setIdVehiculo(202);
            notificacion.setIdInteresado(303);
            notificacion.setTipo("ALERTA");
            notificacion.setFechaCreacion(fecha);
            notificacion.setLeida(true);
            notificacion.setMensaje("Revisión técnica necesaria");

            // Then
            assertThat(notificacion.getId()).isEqualTo(1);
            assertThat(notificacion.getIdEmpleado()).isEqualTo(101);
            assertThat(notificacion.getIdVehiculo()).isEqualTo(202);
            assertThat(notificacion.getIdInteresado()).isEqualTo(303);
            assertThat(notificacion.getTipo()).isEqualTo("ALERTA");
            assertThat(notificacion.getFechaCreacion()).isEqualTo(fecha);
            assertThat(notificacion.isLeida()).isTrue();
            assertThat(notificacion.getMensaje()).isEqualTo("Revisión técnica necesaria");
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode (Lombok)")
    class EqualsHashCodeTest {

        // Método helper para crear una notificación consistente para las pruebas de igualdad
        private Notificacion crearNotificacion(Integer id, String mensaje, LocalDateTime fecha) {
            Notificacion notif = new Notificacion(1, 2, 3, "TIPO_TEST", mensaje);
            notif.setId(id);
            notif.setFechaCreacion(fecha);
            notif.setLeida(false);
            return notif;
        }

        @Test
        @DisplayName("Debería ser igual a otra instancia con los mismos datos")
        void deberiaSerIgualAOtraInstanciaConMismosDatos() {
            // Given
            LocalDateTime fecha = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            Notificacion notif1 = crearNotificacion(100, "Mensaje de prueba", fecha);
            Notificacion notif2 = crearNotificacion(100, "Mensaje de prueba", fecha);

            // Then
            assertThat(notif1).isEqualTo(notif2);
            assertThat(notif1.hashCode()).isEqualTo(notif2.hashCode());
        }

        @Test
        @DisplayName("No debería ser igual a una instancia con datos diferentes")
        void noDeberiaSerIgualAInstanciaConDatosDiferentes() {
            // Given
            LocalDateTime fecha = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            Notificacion notif1 = crearNotificacion(100, "Mensaje de prueba", fecha);
            Notificacion notifDiferente = crearNotificacion(101, "Mensaje diferente", fecha);

            // Then
            assertThat(notif1).isNotEqualTo(notifDiferente);
        }

        @Test
        @DisplayName("No debería ser igual a null")
        void noDeberiaSerIgualANull() {
            // Given
            Notificacion notif1 = crearNotificacion(100, "Mensaje de prueba", LocalDateTime.now());

            // Then
            assertThat(notif1).isNotEqualTo(null);
        }

        @Test
        @DisplayName("No debería ser igual a un objeto de otra clase")
        void noDeberiaSerIgualAObjetoDeOtraClase() {
            // Given
            Notificacion notif1 = crearNotificacion(100, "Mensaje de prueba", LocalDateTime.now());

            // Then
            assertThat(notif1).isNotEqualTo("No soy una notificación");
        }
    }
}
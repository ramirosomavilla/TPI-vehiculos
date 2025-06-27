package serviciopruebas.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Tests para entidad Vehiculo")
class VehiculoTest {

    private Vehiculo vehiculo;
    private LocalDateTime fechaUbicacion;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        fechaUbicacion = LocalDateTime.of(2024, 6, 26, 14, 30);
    }

    @Nested
    @DisplayName("Tests de creación y asignación de propiedades")
    class CreacionYAsignacionTest {

        @Test
        @DisplayName("Debería crear una instancia de Vehiculo")
        void deberiaCrearInstanciaVehiculo() {
            // Given & When
            Vehiculo nuevoVehiculo = new Vehiculo();

            // Then
            assertThat(nuevoVehiculo).isNotNull();
            assertThat(nuevoVehiculo.getId()).isNull();
            assertThat(nuevoVehiculo.getPatente()).isNull();
            assertThat(nuevoVehiculo.getIdModelo()).isNull();
            assertThat(nuevoVehiculo.getAnio()).isNull();
            assertThat(nuevoVehiculo.getLatitud()).isNull();
            assertThat(nuevoVehiculo.getLongitud()).isNull();
            assertThat(nuevoVehiculo.getFechaUbicacion()).isNull();
        }

        @Test
        @DisplayName("Debería asignar y obtener ID correctamente")
        void deberiaAsignarYObtenerIdCorrectamente() {
            // Given
            Integer idEsperado = 123;

            // When
            vehiculo.setId(idEsperado);

            // Then
            assertThat(vehiculo.getId()).isEqualTo(idEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener patente correctamente")
        void deberiaAsignarYObtenerPatenteCorrectamente() {
            // Given
            String patenteEsperada = "ABC123";

            // When
            vehiculo.setPatente(patenteEsperada);

            // Then
            assertThat(vehiculo.getPatente()).isEqualTo(patenteEsperada);
        }

        @Test
        @DisplayName("Debería manejar patentes con diferentes formatos")
        void deberiaManejarPatentesConDiferentesFormatos() {
            // Test patente formato viejo
            vehiculo.setPatente("ABC123");
            assertThat(vehiculo.getPatente()).isEqualTo("ABC123");

            // Test patente formato nuevo
            vehiculo.setPatente("AB123CD");
            assertThat(vehiculo.getPatente()).isEqualTo("AB123CD");

            // Test patente con espacios
            vehiculo.setPatente("ABC 123");
            assertThat(vehiculo.getPatente()).isEqualTo("ABC 123");
        }

        @Test
        @DisplayName("Debería asignar y obtener ID de modelo correctamente")
        void deberiaAsignarYObtenerIdModeloCorrectamente() {
            // Given
            Integer idModeloEsperado = 456;

            // When
            vehiculo.setIdModelo(idModeloEsperado);

            // Then
            assertThat(vehiculo.getIdModelo()).isEqualTo(idModeloEsperado);
        }

        @Test
        @DisplayName("Debería asignar y obtener año correctamente")
        void deberiaAsignarYObtenerAnioCorrectamente() {
            // Given
            Integer anioEsperado = 2020;

            // When
            vehiculo.setAnio(anioEsperado);

            // Then
            assertThat(vehiculo.getAnio()).isEqualTo(anioEsperado);
        }

        @Test
        @DisplayName("Debería validar años válidos")
        void deberiaValidarAniosValidos() {
            // Test año reciente
            vehiculo.setAnio(2024);
            assertThat(vehiculo.getAnio()).isEqualTo(2024);

            // Test año más antiguo pero válido
            vehiculo.setAnio(1990);
            assertThat(vehiculo.getAnio()).isEqualTo(1990);
        }

        @Test
        @DisplayName("Debería asignar y obtener latitud correctamente")
        void deberiaAsignarYObtenerLatitudCorrectamente() {
            // Given
            Double latitudEsperada = -31.4201;

            // When
            vehiculo.setLatitud(latitudEsperada);

            // Then
            assertThat(vehiculo.getLatitud()).isEqualTo(latitudEsperada);
        }

        @Test
        @DisplayName("Debería manejar coordenadas de latitud válidas")
        void deberiaManejarCoordenadasLatitudValidas() {
            // Test latitud positiva
            vehiculo.setLatitud(40.7128);
            assertThat(vehiculo.getLatitud()).isEqualTo(40.7128);

            // Test latitud negativa (hemisferio sur)
            vehiculo.setLatitud(-34.6037);
            assertThat(vehiculo.getLatitud()).isEqualTo(-34.6037);

            // Test latitud cero (ecuador)
            vehiculo.setLatitud(0.0);
            assertThat(vehiculo.getLatitud()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Debería asignar y obtener longitud correctamente")
        void deberiaAsignarYObtenerLongitudCorrectamente() {
            // Given
            Double longitudEsperada = -64.1888;

            // When
            vehiculo.setLongitud(longitudEsperada);

            // Then
            assertThat(vehiculo.getLongitud()).isEqualTo(longitudEsperada);
        }

        @Test
        @DisplayName("Debería manejar coordenadas de longitud válidas")
        void deberiaManejarCoordenadasLongitudValidas() {
            // Test longitud positiva
            vehiculo.setLongitud(74.0060);
            assertThat(vehiculo.getLongitud()).isEqualTo(74.0060);

            // Test longitud negativa
            vehiculo.setLongitud(-58.3816);
            assertThat(vehiculo.getLongitud()).isEqualTo(-58.3816);

            // Test longitud cero (meridiano de Greenwich)
            vehiculo.setLongitud(0.0);
            assertThat(vehiculo.getLongitud()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Debería asignar y obtener fecha de ubicación correctamente")
        void deberiaAsignarYObtenerFechaUbicacionCorrectamente() {
            // When
            vehiculo.setFechaUbicacion(fechaUbicacion);

            // Then
            assertThat(vehiculo.getFechaUbicacion()).isEqualTo(fechaUbicacion);
        }

        @Test
        @DisplayName("Debería manejar valores null para campos opcionales")
        void deberiaManejarValoresNullParaCamposOpcionales() {
            // When
            vehiculo.setLatitud(null);
            vehiculo.setLongitud(null);
            vehiculo.setFechaUbicacion(null);

            // Then
            assertThat(vehiculo.getLatitud()).isNull();
            assertThat(vehiculo.getLongitud()).isNull();
            assertThat(vehiculo.getFechaUbicacion()).isNull();
        }
    }

    @Nested
    @DisplayName("Tests de validación de ubicación")
    class ValidacionUbicacionTest {

        @Test
        @DisplayName("Debería tener ubicación completa cuando todos los campos están presentes")
        void deberiaTenerUbicacionCompletaCuandoTodosLosCamposEstanPresentes() {
            // Given
            vehiculo.setLatitud(-31.4201);
            vehiculo.setLongitud(-64.1888);
            vehiculo.setFechaUbicacion(fechaUbicacion);

            // Then
            assertThat(vehiculo.getLatitud()).isNotNull();
            assertThat(vehiculo.getLongitud()).isNotNull();
            assertThat(vehiculo.getFechaUbicacion()).isNotNull();
        }

        @Test
        @DisplayName("Debería manejar ubicación parcial")
        void deberiaManejarUbicacionParcial() {
            // Given - Solo latitud y longitud, sin fecha
            vehiculo.setLatitud(-31.4201);
            vehiculo.setLongitud(-64.1888);

            // Then
            assertThat(vehiculo.getLatitud()).isNotNull();
            assertThat(vehiculo.getLongitud()).isNotNull();
            assertThat(vehiculo.getFechaUbicacion()).isNull();
        }

        @Test
        @DisplayName("Debería actualizar ubicación correctamente")
        void deberiaActualizarUbicacionCorrectamente() {
            // Given - ubicación inicial
            vehiculo.setLatitud(-31.4201);
            vehiculo.setLongitud(-64.1888);
            vehiculo.setFechaUbicacion(fechaUbicacion);

            // When - actualizar ubicación
            Double nuevaLatitud = -31.4250;
            Double nuevaLongitud = -64.1900;
            LocalDateTime nuevaFecha = fechaUbicacion.plusMinutes(5);

            vehiculo.setLatitud(nuevaLatitud);
            vehiculo.setLongitud(nuevaLongitud);
            vehiculo.setFechaUbicacion(nuevaFecha);

            // Then
            assertThat(vehiculo.getLatitud()).isEqualTo(nuevaLatitud);
            assertThat(vehiculo.getLongitud()).isEqualTo(nuevaLongitud);
            assertThat(vehiculo.getFechaUbicacion()).isEqualTo(nuevaFecha);
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode (Lombok)")
    class EqualsHashCodeTest {

        @Test
        @DisplayName("Debería ser igual a sí mismo")
        void deberiaSerIgualASiMismo() {
            // Given
            configurarVehiculo(vehiculo);

            // Then
            assertThat(vehiculo).isEqualTo(vehiculo);
            assertThat(vehiculo.hashCode()).isEqualTo(vehiculo.hashCode());
        }

        @Test
        @DisplayName("Debería ser igual a otra instancia con mismos datos")
        void deberiaSerIgualAOtraInstanciaConMismosDatos() {
            // Given
            configurarVehiculo(vehiculo);

            Vehiculo otroVehiculo = new Vehiculo();
            configurarVehiculo(otroVehiculo);

            // Then
            assertThat(vehiculo).isEqualTo(otroVehiculo);
            assertThat(vehiculo.hashCode()).isEqualTo(otroVehiculo.hashCode());
        }

        @Test
        @DisplayName("No debería ser igual a null")
        void noDeberiaSerIgualANull() {
            // Given
            configurarVehiculo(vehiculo);

            // Then
            assertThat(vehiculo).isNotEqualTo(null);
        }

        @Test
        @DisplayName("No debería ser igual a objeto de diferente clase")
        void noDeberiaSerIgualAObjetoDiferenteClase() {
            // Given
            configurarVehiculo(vehiculo);
            String otroObjeto = "No soy un vehículo";

            // Then
            assertThat(vehiculo).isNotEqualTo(otroObjeto);
        }

        private void configurarVehiculo(Vehiculo vehiculo) {
            vehiculo.setId(1);
            vehiculo.setPatente("ABC123");
            vehiculo.setIdModelo(100);
            vehiculo.setAnio(2020);
            vehiculo.setLatitud(-31.4201);
            vehiculo.setLongitud(-64.1888);
            vehiculo.setFechaUbicacion(fechaUbicacion);
        }
    }

    @Nested
    @DisplayName("Tests de casos extremos")
    class CasosExtremosTest {

        @Test
        @DisplayName("Debería manejar patente muy larga")
        void deberiaManejarPatenteMuyLarga() {
            // Given
            String patenteLarga = "ABCDEFGHIJKLMNOP123456789";

            // When
            vehiculo.setPatente(patenteLarga);

            // Then
            assertThat(vehiculo.getPatente()).isEqualTo(patenteLarga);
        }

        @Test
        @DisplayName("Debería manejar año muy antiguo")
        void deberiaManejarAnioMuyAntiguo() {
            // Given
            Integer anioAntiguo = 1900;

            // When
            vehiculo.setAnio(anioAntiguo);

            // Then
            assertThat(vehiculo.getAnio()).isEqualTo(anioAntiguo);
        }

        @Test
        @DisplayName("Debería manejar coordenadas extremas")
        void deberiaManejarCoordenadasExtremas() {
            // Given
            Double latitudExtrema = 90.0; // Polo Norte
            Double longitudExtrema = 180.0; // Antimeridiano

            // When
            vehiculo.setLatitud(latitudExtrema);
            vehiculo.setLongitud(longitudExtrema);

            // Then
            assertThat(vehiculo.getLatitud()).isEqualTo(latitudExtrema);
            assertThat(vehiculo.getLongitud()).isEqualTo(longitudExtrema);
        }

        @Test
        @DisplayName("Debería manejar fecha muy futura")
        void deberiaManejarFechaMuyFutura() {
            // Given
            LocalDateTime fechaFutura = LocalDateTime.of(2100, 12, 31, 23, 59);

            // When
            vehiculo.setFechaUbicacion(fechaFutura);

            // Then
            assertThat(vehiculo.getFechaUbicacion()).isEqualTo(fechaFutura);
        }
    }
}
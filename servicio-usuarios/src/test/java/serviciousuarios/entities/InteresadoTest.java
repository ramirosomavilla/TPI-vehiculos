package serviciousuarios.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import serviciousuarios.dtos.InteresadoDTO;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test para la entidad Interesado")
public class InteresadoTest {

    private Interesado interesado;

    @BeforeEach
    void setUp() {
        interesado = new Interesado();
    }

    @Nested
    @DisplayName("Tests de creación y asignación de propiedades")
    class CreacionYAsignacionTest {

        @Test
        @DisplayName("Debería crear una instancia de Interesado con valores por defecto")
        void deberiaCrearInstanciaInteresado() {
            // Given & When
            Interesado nuevoInteresado = new Interesado();

            // Then
            assertThat(nuevoInteresado).isNotNull();
            assertThat(nuevoInteresado.getId()).isEqualTo(0);
            assertThat(nuevoInteresado.getTipoDocumento()).isNull();
            assertThat(nuevoInteresado.getDocumento()).isNull();
            assertThat(nuevoInteresado.getNombre()).isNull();
            assertThat(nuevoInteresado.getApellido()).isNull();
            assertThat(nuevoInteresado.getRestringido()).isEqualTo(0);
            assertThat(nuevoInteresado.getNroLicencia()).isEqualTo(0);
            assertThat(nuevoInteresado.getFechaVencimientoLicencia()).isNull();
        }

        @Test
        @DisplayName("Debería asignar y obtener las propiedades correctamente")
        void deberiaAsignarYObtenerPropiedadesCorrectamente() {
            // Given
            int id = 1;
            String tipoDoc = "DNI";
            String doc = "12345678";
            String nombre = "Juan";
            String apellido = "Perez";
            int restringido = 1;
            int nroLicencia = 98765;
            String fechaVenc = "2025-12-31";

            // When
            interesado.setId(id);
            interesado.setTipoDocumento(tipoDoc);
            interesado.setDocumento(doc);
            interesado.setNombre(nombre);
            interesado.setApellido(apellido);
            interesado.setRestringido(restringido);
            interesado.setNroLicencia(nroLicencia);
            interesado.setFechaVencimientoLicencia(fechaVenc);

            // Then
            assertThat(interesado.getId()).isEqualTo(id);
            assertThat(interesado.getTipoDocumento()).isEqualTo(tipoDoc);
            assertThat(interesado.getDocumento()).isEqualTo(doc);
            assertThat(interesado.getNombre()).isEqualTo(nombre);
            assertThat(interesado.getApellido()).isEqualTo(apellido);
            assertThat(interesado.getRestringido()).isEqualTo(restringido);
            assertThat(interesado.getNroLicencia()).isEqualTo(nroLicencia);
            assertThat(interesado.getFechaVencimientoLicencia()).isEqualTo(fechaVenc);
        }
    }

    @Nested
    @DisplayName("Tests del método ToDTO")
    class ToDTOTest {

        @Test
        @DisplayName("Debería convertir una entidad completa a DTO correctamente")
        void deberiaConvertirEntidadCompletaADTO() {
            // Given
            interesado.setId(101);
            interesado.setTipoDocumento("PASAPORTE");
            interesado.setDocumento("ABC12345");
            interesado.setNombre("Ana");
            interesado.setApellido("García");
            interesado.setRestringido(0);
            interesado.setNroLicencia(555444);
            interesado.setFechaVencimientoLicencia("2030-01-15");

            // When
            InteresadoDTO dto = interesado.ToDTO();

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(101);
            assertThat(dto.getTipoDocumento()).isEqualTo("PASAPORTE");
            assertThat(dto.getDocumento()).isEqualTo("ABC12345");
            assertThat(dto.getNombre()).isEqualTo("Ana");
            assertThat(dto.getApellido()).isEqualTo("García");
            assertThat(dto.getRestringido()).isEqualTo(0);
            assertThat(dto.getNroLicencia()).isEqualTo(555444);
            assertThat(dto.getFechaVencimientoLicencia()).isEqualTo("2030-01-15");
        }

        @Test
        @DisplayName("Debería convertir una entidad con valores nulos a DTO")
        void deberiaConvertirEntidadConValoresNulosADTO() {
            // Given
            interesado.setId(102);
            // El resto de los campos son nulos/cero por defecto

            // When
            InteresadoDTO dto = interesado.ToDTO();

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(102);
            assertThat(dto.getTipoDocumento()).isNull();
            assertThat(dto.getDocumento()).isNull();
            assertThat(dto.getNombre()).isNull();
            assertThat(dto.getApellido()).isNull();
            assertThat(dto.getRestringido()).isEqualTo(0);
            assertThat(dto.getNroLicencia()).isEqualTo(0);
            assertThat(dto.getFechaVencimientoLicencia()).isNull();
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode (Lombok)")
    class EqualsHashCodeTest {

        // Método helper para configurar una instancia completa
        private void configurarInteresado(Interesado interesado, int id, String nombre, String apellido) {
            interesado.setId(id);
            interesado.setNombre(nombre);
            interesado.setApellido(apellido);
            interesado.setTipoDocumento("DNI");
            interesado.setDocumento("11223344");
            interesado.setRestringido(0);
            interesado.setNroLicencia(123);
            interesado.setFechaVencimientoLicencia("2028-10-10");
        }

        @Test
        @DisplayName("Debería ser igual a otra instancia con los mismos datos")
        void deberiaSerIgualAOtraInstanciaConMismosDatos() {
            // Given
            Interesado interesado1 = new Interesado();
            configurarInteresado(interesado1, 200, "Carlos", "Paz");

            Interesado interesado2 = new Interesado();
            configurarInteresado(interesado2, 200, "Carlos", "Paz");

            // Then
            assertThat(interesado1).isEqualTo(interesado2);
            assertThat(interesado1.hashCode()).isEqualTo(interesado2.hashCode());
        }

        @Test
        @DisplayName("No debería ser igual a una instancia con datos diferentes")
        void noDeberiaSerIgualAInstanciaConDatosDiferentes() {
            // Given
            Interesado interesado1 = new Interesado();
            configurarInteresado(interesado1, 200, "Carlos", "Paz");

            Interesado interesadoDiferente = new Interesado();
            configurarInteresado(interesadoDiferente, 201, "Maria", "Lopez");

            // Then
            assertThat(interesado1).isNotEqualTo(interesadoDiferente);
        }

        @Test
        @DisplayName("No debería ser igual a null")
        void noDeberiaSerIgualANull() {
            // Given
            configurarInteresado(interesado, 200, "Carlos", "Paz");

            // Then
            assertThat(interesado).isNotEqualTo(null);
        }

        @Test
        @DisplayName("No debería ser igual a un objeto de otra clase")
        void noDeberiaSerIgualAObjetoDeOtraClase() {
            // Given
            configurarInteresado(interesado, 200, "Carlos", "Paz");

            // Then
            assertThat(interesado).isNotEqualTo(new Object());
        }
    }
}
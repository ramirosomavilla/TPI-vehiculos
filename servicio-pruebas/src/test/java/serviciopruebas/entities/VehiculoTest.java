package serviciopruebas.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VehiculoTest {

    @Test
    @DisplayName("Getters y setters funcionan correctamente")
    void testGettersAndSetters() {
        Vehiculo v = new Vehiculo();
        LocalDateTime now = LocalDateTime.of(2025, 6, 27, 18, 0);

        v.setId(7);
        v.setPatente("ZZZ999");
        v.setIdModelo(42);
        v.setAnio(2022);
        v.setLatitud(34.56);
        v.setLongitud(78.90);
        v.setFechaUbicacion(now);

        assertEquals(7, v.getId());
        assertEquals("ZZZ999", v.getPatente());
        assertEquals(42, v.getIdModelo());
        assertEquals(2022, v.getAnio());
        assertEquals(34.56, v.getLatitud());
        assertEquals(78.90, v.getLongitud());
        assertEquals(now, v.getFechaUbicacion());
    }

    @Test
    @DisplayName("equals y hashCode consideran todos los campos")
    void testEqualsAndHashCode() {
        LocalDateTime ts = LocalDateTime.of(2025, 1, 1, 12, 0);

        Vehiculo a = new Vehiculo();
        a.setId(1);
        a.setPatente("AAA111");
        a.setIdModelo(5);
        a.setAnio(2020);
        a.setLatitud(1.1);
        a.setLongitud(2.2);
        a.setFechaUbicacion(ts);

        Vehiculo b = new Vehiculo();
        b.setId(1);
        b.setPatente("AAA111");
        b.setIdModelo(5);
        b.setAnio(2020);
        b.setLatitud(1.1);
        b.setLongitud(2.2);
        b.setFechaUbicacion(ts);

        // Reflexiva
        assertEquals(a, a);
        // Simétrica
        assertEquals(a, b);
        assertEquals(b, a);
        // Hash codes iguales
        assertEquals(a.hashCode(), b.hashCode());

        // Cambiamos un campo y ya no son iguales
        b.setPatente("BBB222");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @DisplayName("toString incluye todos los campos y sus valores")
    void testToStringContainsAllFields() {
        LocalDateTime ts = LocalDateTime.of(2025, 12, 31, 23, 59);

        Vehiculo v = new Vehiculo();
        v.setId(99);
        v.setPatente("TTT123");
        v.setIdModelo(77);
        v.setAnio(2023);
        v.setLatitud(11.22);
        v.setLongitud(33.44);
        v.setFechaUbicacion(ts);

        String str = v.toString();
        assertTrue(str.contains("id=99"));
        assertTrue(str.contains("patente=TTT123"));
        assertTrue(str.contains("idModelo=77"));
        assertTrue(str.contains("anio=2023"));
        assertTrue(str.contains("latitud=11.22"));
        assertTrue(str.contains("longitud=33.44"));
        assertTrue(str.contains("fechaUbicacion=" + ts.toString()));
    }
}
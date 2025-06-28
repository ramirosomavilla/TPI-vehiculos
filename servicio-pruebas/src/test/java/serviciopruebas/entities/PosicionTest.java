package serviciopruebas.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PosicionTest {

    @Test
    @DisplayName("Getters y setters funcionan correctamente")
    void testGettersAndSetters() {
        Posicion p = new Posicion();
        LocalDateTime now = LocalDateTime.of(2025, 6, 27, 17, 30);

        p.setId(42);
        p.setIdVehiculo(100);
        p.setLatitud(12.3456);
        p.setLongitud(65.4321);
        p.setFechaHora(now);

        assertEquals(42, p.getId());
        assertEquals(100, p.getIdVehiculo());
        assertEquals(12.3456, p.getLatitud());
        assertEquals(65.4321, p.getLongitud());
        assertEquals(now, p.getFechaHora());
    }

    @Test
    @DisplayName("equals y hashCode se basan en todos los campos")
    void testEqualsAndHashCode() {
        LocalDateTime ts = LocalDateTime.of(2025, 6, 27, 17, 30);

        Posicion a = new Posicion();
        a.setId(1);
        a.setIdVehiculo(10);
        a.setLatitud(1.1);
        a.setLongitud(2.2);
        a.setFechaHora(ts);

        Posicion b = new Posicion();
        b.setId(1);
        b.setIdVehiculo(10);
        b.setLatitud(1.1);
        b.setLongitud(2.2);
        b.setFechaHora(ts);

        // Igualdad reflexiva
        assertEquals(a, a);
        // Igualdad simétrica
        assertEquals(a, b);
        assertEquals(b, a);
        // hashCode iguales
        assertEquals(a.hashCode(), b.hashCode());

        // Cambiamos un solo campo y dejamos de ser iguales
        b.setLongitud(3.3);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @DisplayName("toString incluye todos los valores de los campos")
    void testToStringContainsAllFields() {
        LocalDateTime ts = LocalDateTime.of(2025, 6, 27, 17, 30);

        Posicion p = new Posicion();
        p.setId(99);
        p.setIdVehiculo(55);
        p.setLatitud(9.9);
        p.setLongitud(8.8);
        p.setFechaHora(ts);

        String str = p.toString();
        assertTrue(str.contains("id=99"));
        assertTrue(str.contains("idVehiculo=55"));
        assertTrue(str.contains("latitud=9.9"));
        assertTrue(str.contains("longitud=8.8"));
        assertTrue(str.contains("fechaHora=" + ts.toString()));
    }
}
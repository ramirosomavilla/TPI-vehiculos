package serviciopruebas.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import serviciopruebas.dtos.PosicionDTO;
import serviciopruebas.dtos.VehiculoDTO;
import serviciopruebas.entities.Vehiculo;
import serviciopruebas.services.VehiculoService;

@ExtendWith(MockitoExtension.class)
class VehiculoControllerTest {

    @Mock
    private VehiculoService vehiculoService;

    @InjectMocks
    private VehiculoController controller;

    private Vehiculo sampleEntity;
    private VehiculoDTO sampleDto;
    private PosicionDTO samplePosicionDto;

    @BeforeEach
    void setUp() {
        // entidad base
        sampleEntity = new Vehiculo();
        sampleEntity.setId(1);
        sampleEntity.setPatente("ABC123");
        sampleEntity.setIdModelo(10);
        sampleEntity.setAnio(2020);
        sampleEntity.setLatitud(12.34);
        sampleEntity.setLongitud(56.78);
        sampleEntity.setFechaUbicacion(LocalDateTime.of(2025, 1, 1, 8, 0));

        // DTO base
        sampleDto = new VehiculoDTO();
        sampleDto.setId(1);
        sampleDto.setPatente("ABC123");
        sampleDto.setIdModelo(10);
        sampleDto.setAnio(2020);
        sampleDto.setLatitud(12.34);
        sampleDto.setLongitud(56.78);
        sampleDto.setFechaUbicacion(LocalDateTime.of(2025, 1, 1, 8, 0));

        // Posición DTO
        samplePosicionDto = new PosicionDTO();
        samplePosicionDto.setLatitud(90.0);
        samplePosicionDto.setLongitud(45.0);
        samplePosicionDto.setFechaHora(LocalDateTime.of(2025, 6, 1, 12, 30));
    }

    @Test
    @DisplayName("getAll() debe devolver todos los vehículos mapeados a DTO")
    void testGetAll() {
        Vehiculo otra = new Vehiculo();
        otra.setId(2);
        otra.setPatente("XYZ789");
        given(vehiculoService.findAll()).willReturn(Arrays.asList(sampleEntity, otra));

        Iterable<VehiculoDTO> iterable = controller.getAll();
        List<VehiculoDTO> dtos = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(2, dtos.size());
        assertEquals("ABC123", dtos.get(0).getPatente());
        assertEquals("XYZ789", dtos.get(1).getPatente());
        then(vehiculoService).should().findAll();
    }

    @Test
    @DisplayName("getById() devuelve DTO cuando existe y null cuando no")
    void testGetById() {
        // caso encontrado
        given(vehiculoService.findById(1)).willReturn(Optional.of(sampleEntity));
        VehiculoDTO dto = controller.getById(1);
        assertNotNull(dto);
        assertEquals("ABC123", dto.getPatente());

        // caso no encontrado
        given(vehiculoService.findById(99)).willReturn(Optional.empty());
        assertNull(controller.getById(99));

        then(vehiculoService).should(times(2)).findById(anyInt());
    }

    @Test
    @DisplayName("create() debe guardar y devolver DTO con ID asignado")
    void testCreate() {
        Vehiculo saved = new Vehiculo();
        saved.setId(5);
        saved.setPatente(sampleDto.getPatente());
        saved.setIdModelo(sampleDto.getIdModelo());
        saved.setAnio(sampleDto.getAnio());

        given(vehiculoService.save(any(Vehiculo.class))).willReturn(saved);

        VehiculoDTO result = controller.create(sampleDto);
        assertEquals(5, result.getId());
        assertEquals("ABC123", result.getPatente());
        then(vehiculoService).should().save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("delete() debe invocar deleteById")
    void testDelete() {
        willDoNothing().given(vehiculoService).deleteById(4);
        controller.delete(4);
        then(vehiculoService).should().deleteById(4);
    }

    @Test
    @DisplayName("recibirPosicion() guarda posición y devuelve DTO actualizado")
    void testRecibirPosicion() {
        Vehiculo updated = new Vehiculo();
        updated.setId(1);
        updated.setPatente("ABC123");
        updated.setIdModelo(10);
        updated.setAnio(2020);
        updated.setLatitud(samplePosicionDto.getLatitud());
        updated.setLongitud(samplePosicionDto.getLongitud());
        updated.setFechaUbicacion(samplePosicionDto.getFechaHora());

        given(vehiculoService.guardarPosicion(
                eq(1),
                eq(samplePosicionDto.getLatitud()),
                eq(samplePosicionDto.getLongitud()),
                eq(samplePosicionDto.getFechaHora())
        )).willReturn(updated);

        VehiculoDTO result = controller.recibirPosicion(1, samplePosicionDto);
        assertEquals(1, result.getId());
        assertEquals(90.0, result.getLatitud());
        assertEquals(45.0, result.getLongitud());
        then(vehiculoService).should().guardarPosicion(
                1,
                samplePosicionDto.getLatitud(),
                samplePosicionDto.getLongitud(),
                samplePosicionDto.getFechaHora()
        );
    }

    @Test
    @DisplayName("update() responde 200 cuando existe y 404 cuando no")
    void testUpdate() {
        // Caso existe
        Vehiculo existing = new Vehiculo();
        existing.setId(2);
        given(vehiculoService.findById(2)).willReturn(Optional.of(existing));
        Vehiculo saved = new Vehiculo();
        saved.setId(2);
        saved.setPatente("NEW123");
        saved.setIdModelo(20);
        saved.setAnio(2021);
        given(vehiculoService.save(any(Vehiculo.class))).willReturn(saved);

        VehiculoDTO dto = new VehiculoDTO();
        dto.setPatente("NEW123");
        dto.setIdModelo(20);
        dto.setAnio(2021);

        ResponseEntity<VehiculoDTO> resp = controller.update(2, dto);
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("NEW123", resp.getBody().getPatente());

        // Caso no existe
        given(vehiculoService.findById(99)).willReturn(Optional.empty());
        ResponseEntity<VehiculoDTO> notFound = controller.update(99, dto);
        assertEquals(404, notFound.getStatusCodeValue());

        then(vehiculoService).should(times(2)).findById(anyInt());
    }

    @Test
    @DisplayName("partialUpdate() modifica solo campos no nulos y responde 404 si no existe")
    void testPartialUpdate() {
        Vehiculo existing = new Vehiculo();
        existing.setId(3);
        existing.setPatente("OLD");
        existing.setIdModelo(30);
        existing.setAnio(2019);
        existing.setLatitud(0.0);
        existing.setLongitud(0.0);
        existing.setFechaUbicacion(LocalDateTime.of(2024,1,1,0,0));

        given(vehiculoService.findById(3)).willReturn(Optional.of(existing));
        // espejo: save devuelve mismo objeto modificado
        given(vehiculoService.save(any(Vehiculo.class))).willAnswer(inv -> inv.getArgument(0));

        VehiculoDTO patch = new VehiculoDTO();
        patch.setPatente("NEW-P");
        patch.setAnio(2023);

        ResponseEntity<VehiculoDTO> resp = controller.partialUpdate(3, patch);
        assertEquals(200, resp.getStatusCodeValue());
        VehiculoDTO body = resp.getBody();
        assertEquals("NEW-P", body.getPatente());
        assertEquals(2023, body.getAnio());
        assertEquals(30, body.getIdModelo());        // inalterado
        assertEquals(0.0, body.getLatitud());

        // caso no existe
        given(vehiculoService.findById(100)).willReturn(Optional.empty());
        ResponseEntity<VehiculoDTO> nf = controller.partialUpdate(100, patch);
        assertEquals(404, nf.getStatusCodeValue());

        then(vehiculoService).should(times(2)).findById(anyInt());
    }
    @Test
    @DisplayName("partialUpdate cubre todos los campos cuando no son nulos")
    void testPartialUpdate_AllFieldsNotNull() {
        // 1) Prepara entidad existente
        LocalDateTime now = LocalDateTime.of(2025, 7, 1, 10, 0);
        Vehiculo existing = new Vehiculo();
        existing.setId(3);
        existing.setPatente("OLD");
        existing.setIdModelo(11);
        existing.setAnio(2021);
        existing.setLatitud(1.0);
        existing.setLongitud(2.0);
        existing.setFechaUbicacion(LocalDateTime.of(2025,1,1,0,0));

        given(vehiculoService.findById(3)).willReturn(Optional.of(existing));
        // El save devuelve la entidad modificada
        given(vehiculoService.save(any(Vehiculo.class)))
                .willAnswer(inv -> inv.getArgument(0));

        // 2) DTO con todos los campos no nulos
        VehiculoDTO patch = new VehiculoDTO();
        patch.setPatente("NEW-P");
        patch.setIdModelo(99);
        patch.setAnio(2030);
        patch.setLatitud(55.5);
        patch.setLongitud(66.6);
        patch.setFechaUbicacion(now);

        // 3) Ejecuta
        ResponseEntity<VehiculoDTO> resp = controller.partialUpdate(3, patch);
        VehiculoDTO body = resp.getBody();

        // 4) Verifica que todos los campos se actualizaron
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("NEW-P",   body.getPatente());
        assertEquals(99,        body.getIdModelo());
        assertEquals(2030,      body.getAnio());
        assertEquals(55.5,      body.getLatitud());
        assertEquals(66.6,      body.getLongitud());
        assertEquals(now,       body.getFechaUbicacion());

        // 5) Verifica interacciones
        then(vehiculoService).should().findById(3);
        then(vehiculoService).should().save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("partialUpdate no modifica nada cuando DTO tiene todos campos null")
    void testPartialUpdate_AllFieldsNull() {
        // 1) Entidad de partida
        LocalDateTime origTs = LocalDateTime.of(2024, 12, 31, 23, 59);
        Vehiculo existing = new Vehiculo();
        existing.setId(4);
        existing.setPatente("UNCHANGED");
        existing.setIdModelo(22);
        existing.setAnio(2022);
        existing.setLatitud(9.9);
        existing.setLongitud(8.8);
        existing.setFechaUbicacion(origTs);

        given(vehiculoService.findById(4)).willReturn(Optional.of(existing));
        given(vehiculoService.save(any(Vehiculo.class)))
                .willAnswer(inv -> inv.getArgument(0));

        // 2) DTO “vacío”
        VehiculoDTO patch = new VehiculoDTO();
        // todos los getters devuelven null

        // 3) Ejecuta
        ResponseEntity<VehiculoDTO> resp = controller.partialUpdate(4, patch);
        VehiculoDTO body = resp.getBody();

        // 4) Verifica que nada cambió
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("UNCHANGED", body.getPatente());
        assertEquals(22,         body.getIdModelo());
        assertEquals(2022,       body.getAnio());
        assertEquals(9.9,        body.getLatitud());
        assertEquals(8.8,        body.getLongitud());
        assertEquals(origTs,     body.getFechaUbicacion());

        // 5) Interacciones
        then(vehiculoService).should().findById(4);
        then(vehiculoService).should().save(any(Vehiculo.class));
    }

}
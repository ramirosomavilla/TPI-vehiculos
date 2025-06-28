package serviciopruebas.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import serviciopruebas.dtos.PruebaDTO;
import serviciopruebas.entities.Prueba;
import serviciopruebas.services.PruebaService;

@ExtendWith(MockitoExtension.class)
class PruebaControllerTest {

    @Mock
    private PruebaService pruebaService;

    @InjectMocks
    private PruebaController controller;

    private PruebaDTO sampleDto;
    private Prueba sampleEntity;

    @BeforeEach
    void setUp() {
        sampleDto = new PruebaDTO();
        sampleDto.setId(1);
        sampleDto.setIdVehiculo(10);
        sampleDto.setIdInteresado(20);
        sampleDto.setIdEmpleado(30);
        sampleDto.setFechaHoraInicio(LocalDateTime.now());
        sampleDto.setFechaHoraFin(LocalDateTime.now().plusHours(1));
        sampleDto.setComentarios("Ok");

        sampleEntity = new Prueba();
        sampleEntity.setId(1);
        sampleEntity.setIdVehiculo(10);
        sampleEntity.setIdInteresado(20);
        sampleEntity.setIdEmpleado(30);
        sampleEntity.setFechaHoraInicio(sampleDto.getFechaHoraInicio());
        sampleEntity.setFechaHoraFin(sampleDto.getFechaHoraFin());
        sampleEntity.setComentarios("Ok");
    }

    @Test
    @DisplayName("create() debe devolver DTO creado con status 200")
    void testCreate() {
        given(pruebaService.create(sampleDto)).willReturn(sampleDto);

        ResponseEntity<PruebaDTO> resp = controller.create(sampleDto);
        assertNotNull(resp);
        assertEquals(200, resp.getStatusCodeValue());
        assertSame(sampleDto, resp.getBody());

        then(pruebaService).should().create(sampleDto);
    }

    @Test
    @DisplayName("getAll() debe mapear entidades a DTOs")
    void testGetAll() {
        Prueba otra = new Prueba();
        otra.setId(2);
        otra.setIdVehiculo(11);
        List<Prueba> lista = Arrays.asList(sampleEntity, otra);
        given(pruebaService.findAll()).willReturn(lista);

        List<PruebaDTO> dtos = controller.getAll();
        assertEquals(2, dtos.size());
        assertEquals(sampleEntity.getId(), dtos.get(0).getId());
        assertEquals(otra.getId(), dtos.get(1).getId());

        then(pruebaService).should().findAll();
    }

    @Test
    @DisplayName("getPruebasEnCurso() debe devolver lista y status 200")
    void testGetPruebasEnCurso() {
        List<PruebaDTO> listaDto = Collections.singletonList(sampleDto);
        given(pruebaService.getPruebasEnCurso()).willReturn(listaDto);

        ResponseEntity<List<PruebaDTO>> resp = controller.getPruebasEnCurso();
        assertEquals(200, resp.getStatusCodeValue());
        assertSame(listaDto, resp.getBody());

        then(pruebaService).should().getPruebasEnCurso();
    }

    @Test
    @DisplayName("finalizar() debe invocar service.finalizar y devolver DTO")
    void testFinalizar() {
        String comentarios = "Listo";
        PruebaDTO finished = new PruebaDTO();
        finished.setId(5);
        given(pruebaService.finalizar(5, comentarios)).willReturn(finished);

        ResponseEntity<PruebaDTO> resp = controller.finalizar(5, comentarios);
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(5, resp.getBody().getId());

        then(pruebaService).should().finalizar(5, comentarios);
    }

    @Test
    @DisplayName("vehiculoEnPrueba() debe devolver Boolean")
    void testVehiculoEnPrueba() {
        given(pruebaService.vehiculoEnPrueba(7)).willReturn(true);

        ResponseEntity<Boolean> resp = controller.vehiculoEnPrueba(7);
        assertTrue(resp.getBody());
        assertEquals(200, resp.getStatusCodeValue());

        then(pruebaService).should().vehiculoEnPrueba(7);
    }

    @Test
    @DisplayName("pruebasPorVehiculo() debe devolver lista de DTOs")
    void testPruebasPorVehiculo() {
        List<PruebaDTO> lista = Collections.singletonList(sampleDto);
        given(pruebaService.pruebasPorVehiculo(10)).willReturn(lista);

        ResponseEntity<List<PruebaDTO>> resp = controller.pruebasPorVehiculo(10);
        assertEquals(lista, resp.getBody());
        assertEquals(200, resp.getStatusCodeValue());

        then(pruebaService).should().pruebasPorVehiculo(10);
    }

    @Test
    @DisplayName("obtenerInteresadoDeVehiculoEnPrueba() debe devolver idInteresado")
    void testObtenerInteresado() {
        Prueba enCurso = new Prueba();
        enCurso.setIdInteresado(99);
        given(pruebaService.obtenerPruebaEnCursoByVehiculoId(3)).willReturn(enCurso);

        ResponseEntity<Integer> resp = controller.obtenerInteresadoDeVehiculoEnPrueba(3);
        assertEquals(99, resp.getBody());
        assertEquals(200, resp.getStatusCodeValue());

        then(pruebaService).should().obtenerPruebaEnCursoByVehiculoId(3);
    }

    @Test
    @DisplayName("delete() debe invocar service.deleteById y devolver 200")
    void testDelete() {
        willDoNothing().given(pruebaService).deleteById(4);

        ResponseEntity<Void> resp = controller.delete(4);
        assertEquals(200, resp.getStatusCodeValue());
        assertNull(resp.getBody());

        then(pruebaService).should().deleteById(4);
    }

    @Test
    @DisplayName("update() debe mapear entidad devuelta a DTO")
    void testUpdate() {
        Prueba updated = new Prueba();
        updated.setId(8);
        updated.setIdVehiculo(15);
        given(pruebaService.update(8, sampleDto)).willReturn(updated);

        ResponseEntity<PruebaDTO> resp = controller.update(8, sampleDto);
        PruebaDTO body = resp.getBody();
        assertEquals(8, body.getId());
        assertEquals(15, body.getIdVehiculo());

        then(pruebaService).should().update(8, sampleDto);
    }

    @Test
    @DisplayName("partialUpdate() debe mapear entidad devuelta a DTO")
    void testPartialUpdate() {
        Prueba updated = new Prueba();
        updated.setId(12);
        updated.setComentarios("Patch");
        given(pruebaService.partialUpdate(12, sampleDto)).willReturn(updated);

        ResponseEntity<PruebaDTO> resp = controller.partialUpdate(12, sampleDto);
        PruebaDTO body = resp.getBody();
        assertEquals(12, body.getId());
        assertEquals("Patch", body.getComentarios());

        then(pruebaService).should().partialUpdate(12, sampleDto);
    }
}

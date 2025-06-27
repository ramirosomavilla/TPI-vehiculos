package serviciopruebas.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import serviciopruebas.repositories.VehiculoRepository;
import serviciopruebas.repositories.PosicionRepository;
import serviciopruebas.entities.Vehiculo;
import serviciopruebas.entities.Posicion;
import serviciopruebas.dtos.AgencyConfigDTO;
import serviciopruebas.dtos.NotificacionRequestDTO;
import serviciopruebas.client.ConfigClient;
import serviciopruebas.client.InteresadoClient;
import serviciopruebas.client.NotificacionClient;
import java.time.LocalDateTime;
import java.util.*;

class VehiculoServiceTest {
  @Mock
  private VehiculoRepository vehiculoRepository;
  @Mock
  private PosicionRepository posicionRepository;
  @Mock
  private PruebaService pruebaservice;
  @Mock
  private ConfigClient configClient;
  @Mock
  private InteresadoClient interesadoClient;
  @Mock
  private NotificacionClient notificacionClient;
  @InjectMocks
  private VehiculoService vehiculoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    List<Vehiculo> vehiculos = Arrays.asList(new Vehiculo(), new Vehiculo());
    when(vehiculoRepository.findAll()).thenReturn(vehiculos);
    List<Vehiculo> result = vehiculoService.findAll();
    assertEquals(2, result.size());
  }

  @Test
  void testFindById() {
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(1);
    when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));
    Optional<Vehiculo> result = vehiculoService.findById(1);
    assertTrue(result.isPresent());
    assertEquals(1, result.get().getId());
  }

  @Test
  void testSave() {
    Vehiculo vehiculo = new Vehiculo();
    when(vehiculoRepository.saveAndFlush(any(Vehiculo.class))).thenReturn(vehiculo);
    Vehiculo result = vehiculoService.save(vehiculo);
    assertNotNull(result);
  }

  @Test
  void testDeleteById() {
    doNothing().when(vehiculoRepository).deleteById(anyInt());
    assertDoesNotThrow(() -> vehiculoService.deleteById(1));
  }

  @Test
  void testGuardarPosicion_VehiculoNoEncontrado() {
    when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.empty());
    Exception ex = assertThrows(RuntimeException.class,
        () -> vehiculoService.guardarPosicion(1, 1.0, 2.0, LocalDateTime.now()));
    assertEquals("Vehiculo no encontrado", ex.getMessage());
  }

  @Test
  void testGuardarPosicion_VehiculoNoEnPrueba() {
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(1);
    when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.of(vehiculo));
    when(pruebaservice.vehiculoEnPrueba(anyInt())).thenReturn(false);
    Exception ex = assertThrows(RuntimeException.class,
        () -> vehiculoService.guardarPosicion(1, 1.0, 2.0, LocalDateTime.now()));
    assertEquals("El vehículo no está en prueba", ex.getMessage());
  }

  @Test
  void testGuardarPosicion_Success() {
    int vehiculoId = 1;
    double lat = 10.0;
    double lng = 20.0;
    LocalDateTime timestamp = LocalDateTime.now();
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(vehiculoId);
    vehiculo.setLatitud(0.0);
    vehiculo.setLongitud(0.0);
    vehiculo.setFechaUbicacion(null);

    AgencyConfigDTO config = new AgencyConfigDTO();
    AgencyConfigDTO.Ubicacion ubicacion = new AgencyConfigDTO.Ubicacion();
    ubicacion.setLatitud(10.0);
    ubicacion.setLongitud(20.0);
    config.setUbicacionAgencia(ubicacion);
    config.setRadioMaximoMetros(100);
    config.setZonasPeligrosas(Collections.emptyList());

    when(vehiculoRepository.findById(vehiculoId)).thenReturn(Optional.of(vehiculo));
    when(pruebaservice.vehiculoEnPrueba(vehiculoId)).thenReturn(true);
    when(configClient.obtenerConfiguracionAgencia()).thenReturn(config);
    when(posicionRepository.save(any(Posicion.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Vehiculo result = vehiculoService.guardarPosicion(vehiculoId, lat, lng, timestamp);
    assertNotNull(result);
    assertEquals(lat, result.getLatitud());
    assertEquals(lng, result.getLongitud());
    assertEquals(timestamp, result.getFechaUbicacion());
  }
}
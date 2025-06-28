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
import serviciopruebas.entities.Prueba;

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

  @Test
  void testGuardarPosicion_FueraDeRadio() {
    int vehiculoId = 1;
    double lat = 100.0; // fuera del radio
    double lng = 200.0;
    LocalDateTime timestamp = LocalDateTime.now();
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(vehiculoId);
    AgencyConfigDTO config = new AgencyConfigDTO();
    AgencyConfigDTO.Ubicacion ubicacion = new AgencyConfigDTO.Ubicacion();
    ubicacion.setLatitud(0.0);
    ubicacion.setLongitud(0.0);
    config.setUbicacionAgencia(ubicacion);
    config.setRadioMaximoMetros(1); // radio muy chico
    config.setZonasPeligrosas(Collections.emptyList());
    Prueba prueba = new Prueba();
    prueba.setIdInteresado(2);
    prueba.setIdEmpleado(3);
    prueba.setIdVehiculo(vehiculoId);
    when(vehiculoRepository.findById(vehiculoId)).thenReturn(Optional.of(vehiculo));
    when(pruebaservice.vehiculoEnPrueba(vehiculoId)).thenReturn(true);
    when(configClient.obtenerConfiguracionAgencia()).thenReturn(config);
    when(pruebaservice.obtenerPruebaEnCursoByVehiculoId(vehiculoId)).thenReturn(prueba);
    doNothing().when(interesadoClient).restringirInteresado(anyInt());
    doNothing().when(notificacionClient).notificarInteresado(any(NotificacionRequestDTO.class));
    when(posicionRepository.save(any(Posicion.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Vehiculo result = vehiculoService.guardarPosicion(vehiculoId, lat, lng, timestamp);
    assertNotNull(result);
    verify(interesadoClient, times(1)).restringirInteresado(2);
    verify(notificacionClient, times(1)).notificarInteresado(any(NotificacionRequestDTO.class));
  }

  @Test
  void testGuardarPosicion_EnZonaPeligrosa() {
    int vehiculoId = 1;
    double lat = 5.0;
    double lng = 5.0;
    LocalDateTime timestamp = LocalDateTime.now();
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(vehiculoId);
    AgencyConfigDTO config = new AgencyConfigDTO();
    AgencyConfigDTO.Ubicacion ubicacion = new AgencyConfigDTO.Ubicacion();
    ubicacion.setLatitud(0.0);
    ubicacion.setLongitud(0.0);
    config.setUbicacionAgencia(ubicacion);
    config.setRadioMaximoMetros(1000);
    AgencyConfigDTO.ZonaPeligrosa zona = new AgencyConfigDTO.ZonaPeligrosa();
    AgencyConfigDTO.Ubicacion coord = new AgencyConfigDTO.Ubicacion();
    coord.setLatitud(5.0);
    coord.setLongitud(5.0);
    zona.setCoordenadas(coord);
    zona.setRadioMetros(10);
    config.setZonasPeligrosas(Collections.singletonList(zona));
    Prueba prueba = new Prueba();
    prueba.setIdInteresado(2);
    prueba.setIdEmpleado(3);
    prueba.setIdVehiculo(vehiculoId);
    when(vehiculoRepository.findById(vehiculoId)).thenReturn(Optional.of(vehiculo));
    when(pruebaservice.vehiculoEnPrueba(vehiculoId)).thenReturn(true);
    when(configClient.obtenerConfiguracionAgencia()).thenReturn(config);
    when(pruebaservice.obtenerPruebaEnCursoByVehiculoId(vehiculoId)).thenReturn(prueba);
    doNothing().when(interesadoClient).restringirInteresado(anyInt());
    doNothing().when(notificacionClient).notificarInteresado(any(NotificacionRequestDTO.class));
    when(posicionRepository.save(any(Posicion.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Vehiculo result = vehiculoService.guardarPosicion(vehiculoId, lat, lng, timestamp);
    assertNotNull(result);
    verify(interesadoClient, times(1)).restringirInteresado(2);
    verify(notificacionClient, times(1)).notificarInteresado(any(NotificacionRequestDTO.class));
  }

  @Test
  void testGuardarPosicion_FueraDeRadioYEnZonaPeligrosa() {
    int vehiculoId = 1;
    double lat = 100.0;
    double lng = 100.0;
    LocalDateTime timestamp = LocalDateTime.now();
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(vehiculoId);
    AgencyConfigDTO config = new AgencyConfigDTO();
    AgencyConfigDTO.Ubicacion ubicacion = new AgencyConfigDTO.Ubicacion();
    ubicacion.setLatitud(0.0);
    ubicacion.setLongitud(0.0);
    config.setUbicacionAgencia(ubicacion);
    config.setRadioMaximoMetros(1);
    AgencyConfigDTO.ZonaPeligrosa zona = new AgencyConfigDTO.ZonaPeligrosa();
    AgencyConfigDTO.Ubicacion coord = new AgencyConfigDTO.Ubicacion();
    coord.setLatitud(100.0);
    coord.setLongitud(100.0);
    zona.setCoordenadas(coord);
    zona.setRadioMetros(10);
    config.setZonasPeligrosas(Collections.singletonList(zona));
    Prueba prueba = new Prueba();
    prueba.setIdInteresado(2);
    prueba.setIdEmpleado(3);
    prueba.setIdVehiculo(vehiculoId);
    when(vehiculoRepository.findById(vehiculoId)).thenReturn(Optional.of(vehiculo));
    when(pruebaservice.vehiculoEnPrueba(vehiculoId)).thenReturn(true);
    when(configClient.obtenerConfiguracionAgencia()).thenReturn(config);
    when(pruebaservice.obtenerPruebaEnCursoByVehiculoId(vehiculoId)).thenReturn(prueba);
    doNothing().when(interesadoClient).restringirInteresado(anyInt());
    doNothing().when(notificacionClient).notificarInteresado(any(NotificacionRequestDTO.class));
    when(posicionRepository.save(any(Posicion.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Vehiculo result = vehiculoService.guardarPosicion(vehiculoId, lat, lng, timestamp);
    assertNotNull(result);
    verify(interesadoClient, times(1)).restringirInteresado(2);
    verify(notificacionClient, times(1)).notificarInteresado(any(NotificacionRequestDTO.class));
  }
}
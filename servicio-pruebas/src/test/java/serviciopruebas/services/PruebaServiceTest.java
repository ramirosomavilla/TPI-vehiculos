package serviciopruebas.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import serviciopruebas.repositories.PruebaRepository;
import serviciopruebas.repositories.VehiculoRepository;
import serviciopruebas.entities.Prueba;
import serviciopruebas.entities.Vehiculo;
import serviciopruebas.dtos.AgencyConfigDTO;
import serviciopruebas.dtos.PruebaDTO;
import serviciopruebas.client.InteresadoClient;
import serviciopruebas.client.ConfigClient;

import java.time.LocalDateTime;
import java.util.*;

class PruebaServiceTest {
  @Mock
  private PruebaRepository pruebaRepository;
  @Mock
  private InteresadoClient interesadoClient;
  @Mock
  private VehiculoRepository vehiculoRepository;
  @Mock
  private ConfigClient configClient;
  @InjectMocks
  private PruebaService pruebaService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreate_Success() {
    PruebaDTO dto = new PruebaDTO();
    dto.setId(100);
    dto.setIdVehiculo(1);
    dto.setIdInteresado(2);
    dto.setIdEmpleado(3);
    dto.setFechaHoraInicio(LocalDateTime.now());
    Prueba prueba = dto.toEntity();
    AgencyConfigDTO config = new AgencyConfigDTO();
    AgencyConfigDTO.Ubicacion ubicacion = new AgencyConfigDTO.Ubicacion();
    ubicacion.setLatitud(1.0);
    ubicacion.setLongitud(2.0);
    config.setUbicacionAgencia(ubicacion);
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setId(1);
    when(interesadoClient.interesadoHasExpiredLicense(anyInt())).thenReturn(false);
    when(interesadoClient.interesadoIsRestricted(anyInt())).thenReturn(false);
    when(pruebaRepository.existsByIdVehiculoAndFechaHoraFinIsNull(anyInt())).thenReturn(false);
    when(configClient.obtenerConfiguracionAgencia()).thenReturn(config);
    when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.of(vehiculo));
    when(pruebaRepository.save(any(Prueba.class))).thenReturn(prueba);
    PruebaDTO result = pruebaService.create(dto);
    assertNotNull(result);
  }

  @Test
  void testCreate_ExpiredLicense() {
    PruebaDTO dto = new PruebaDTO();
    dto.setId(101);
    dto.setIdVehiculo(1);
    dto.setIdInteresado(2);
    dto.setIdEmpleado(3);
    dto.setFechaHoraInicio(LocalDateTime.now());
    when(interesadoClient.interesadoHasExpiredLicense(anyInt())).thenReturn(true);
    RuntimeException ex = assertThrows(RuntimeException.class, () -> pruebaService.create(dto));
    assertEquals("El usuario tiene una licencia expirada", ex.getMessage());
  }

  @Test
  void testCreate_RestrictedUser() {
    PruebaDTO dto = new PruebaDTO();
    dto.setId(102);
    dto.setIdVehiculo(1);
    dto.setIdInteresado(2);
    dto.setIdEmpleado(3);
    dto.setFechaHoraInicio(LocalDateTime.now());
    when(interesadoClient.interesadoHasExpiredLicense(anyInt())).thenReturn(false);
    when(interesadoClient.interesadoIsRestricted(anyInt())).thenReturn(true);
    RuntimeException ex = assertThrows(RuntimeException.class, () -> pruebaService.create(dto));
    assertEquals("El usuario está restringido", ex.getMessage());
  }

  @Test
  void testCreate_VehiculoEnUso() {
    PruebaDTO dto = new PruebaDTO();
    dto.setId(103);
    dto.setIdVehiculo(1);
    dto.setIdInteresado(2);
    dto.setIdEmpleado(3);
    dto.setFechaHoraInicio(LocalDateTime.now());
    when(interesadoClient.interesadoHasExpiredLicense(anyInt())).thenReturn(false);
    when(interesadoClient.interesadoIsRestricted(anyInt())).thenReturn(false);
    when(pruebaRepository.existsByIdVehiculoAndFechaHoraFinIsNull(anyInt())).thenReturn(true);
    RuntimeException ex = assertThrows(RuntimeException.class, () -> pruebaService.create(dto));
    assertEquals("El vehículo ya está siendo utilizado en otra prueba", ex.getMessage());
  }

  @Test
  void testFindAll() {
    List<Prueba> pruebas = Arrays.asList(new Prueba(), new Prueba());
    when(pruebaRepository.findAll()).thenReturn(pruebas);
    List<Prueba> result = pruebaService.findAll();
    assertEquals(2, result.size());
  }

  @Test
  void testDeleteById() {
    doNothing().when(pruebaRepository).deleteById(anyInt());
    assertDoesNotThrow(() -> pruebaService.deleteById(1));
  }

  @Test
  void testPartialUpdate_OnlyUpdatesNonNullFields() {
    int id = 1;
    Prueba original = new Prueba();
    original.setId(id);
    original.setIdVehiculo(10);
    original.setIdInteresado(20);
    original.setIdEmpleado(30);
    original.setFechaHoraInicio(LocalDateTime.of(2023, 1, 1, 10, 0));
    original.setFechaHoraFin(LocalDateTime.of(2023, 1, 1, 12, 0));
    original.setComentarios("original");

    PruebaDTO dto = new PruebaDTO();
    dto.setIdVehiculo(99); // Solo este campo se actualiza
    // Los demás quedan null

    when(pruebaRepository.findById(id)).thenReturn(Optional.of(original));
    when(pruebaRepository.save(any(Prueba.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Prueba updated = pruebaService.partialUpdate(id, dto);
    assertEquals(99, updated.getIdVehiculo());
    assertEquals(20, updated.getIdInteresado()); // No cambia
    assertEquals(30, updated.getIdEmpleado()); // No cambia
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), updated.getFechaHoraInicio()); // No cambia
    assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), updated.getFechaHoraFin()); // No cambia
    assertEquals("original", updated.getComentarios()); // No cambia
  }

  @Test
  void testUpdate_Success() {
    int id = 1;
    Prueba original = new Prueba();
    original.setId(id);
    original.setIdVehiculo(10);
    original.setIdInteresado(20);
    original.setIdEmpleado(30);
    original.setFechaHoraInicio(LocalDateTime.of(2023, 1, 1, 10, 0));
    original.setFechaHoraFin(null);
    original.setComentarios("original");

    PruebaDTO dto = new PruebaDTO();
    dto.setIdVehiculo(99);
    dto.setIdInteresado(88);
    dto.setIdEmpleado(77);
    dto.setFechaHoraInicio(LocalDateTime.of(2024, 1, 1, 10, 0));
    dto.setFechaHoraFin(LocalDateTime.of(2024, 1, 1, 12, 0));
    dto.setComentarios("nuevo comentario");

    when(pruebaRepository.findById(id)).thenReturn(Optional.of(original));
    when(pruebaRepository.save(any(Prueba.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Prueba updated = pruebaService.update(id, dto);
    assertEquals(99, updated.getIdVehiculo());
    assertEquals(88, updated.getIdInteresado());
    assertEquals(77, updated.getIdEmpleado());
    assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), updated.getFechaHoraInicio());
    assertEquals(LocalDateTime.of(2024, 1, 1, 12, 0), updated.getFechaHoraFin());
    assertEquals("nuevo comentario", updated.getComentarios());
  }

  @Test
  void testFinalizar_Success() {
    int id = 1;
    Prueba prueba = new Prueba();
    prueba.setId(id);
    prueba.setFechaHoraFin(null);
    prueba.setComentarios(null);
    when(pruebaRepository.findById(id)).thenReturn(Optional.of(prueba));
    when(pruebaRepository.save(any(Prueba.class))).thenAnswer(invocation -> invocation.getArgument(0));
    PruebaDTO result = pruebaService.finalizar(id, "comentario final");
    assertNotNull(result.getFechaHoraFin());
    assertEquals("comentario final", result.getComentarios());
  }

  @Test
  void testFinalizar_AlreadyFinished() {
    int id = 1;
    Prueba prueba = new Prueba();
    prueba.setId(id);
    prueba.setFechaHoraFin(LocalDateTime.now());
    when(pruebaRepository.findById(id)).thenReturn(Optional.of(prueba));
    RuntimeException ex = assertThrows(RuntimeException.class, () -> pruebaService.finalizar(id, "comentario"));
    assertEquals("La prueba ya ha sido finalizada", ex.getMessage());
  }

  @Test
  void testPartialUpdate_AllFieldsNonNull() {
    int id = 2;
    Prueba original = new Prueba();
    original.setId(id);
    original.setIdVehiculo(10);
    original.setIdInteresado(20);
    original.setIdEmpleado(30);
    original.setFechaHoraInicio(LocalDateTime.of(2023, 1, 1, 10, 0));
    original.setFechaHoraFin(LocalDateTime.of(2023, 1, 1, 12, 0));
    original.setComentarios("original");

    PruebaDTO dto = new PruebaDTO();
    dto.setIdVehiculo(99);
    dto.setIdInteresado(88);
    dto.setIdEmpleado(77);
    dto.setFechaHoraInicio(LocalDateTime.of(2024, 1, 1, 10, 0));
    dto.setFechaHoraFin(LocalDateTime.of(2024, 1, 1, 12, 0));
    dto.setComentarios("nuevo comentario");

    when(pruebaRepository.findById(id)).thenReturn(Optional.of(original));
    when(pruebaRepository.save(any(Prueba.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Prueba updated = pruebaService.partialUpdate(id, dto);
    assertEquals(99, updated.getIdVehiculo());
    assertEquals(88, updated.getIdInteresado());
    assertEquals(77, updated.getIdEmpleado());
    assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), updated.getFechaHoraInicio());
    assertEquals(LocalDateTime.of(2024, 1, 1, 12, 0), updated.getFechaHoraFin());
    assertEquals("nuevo comentario", updated.getComentarios());
  }

  @Test
  void testPartialUpdate_AllFieldsNull() {
    int id = 3;
    Prueba original = new Prueba();
    original.setId(id);
    original.setIdVehiculo(10);
    original.setIdInteresado(20);
    original.setIdEmpleado(30);
    original.setFechaHoraInicio(LocalDateTime.of(2023, 1, 1, 10, 0));
    original.setFechaHoraFin(LocalDateTime.of(2023, 1, 1, 12, 0));
    original.setComentarios("original");

    PruebaDTO dto = new PruebaDTO(); // Todos los campos null

    when(pruebaRepository.findById(id)).thenReturn(Optional.of(original));
    when(pruebaRepository.save(any(Prueba.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Prueba updated = pruebaService.partialUpdate(id, dto);
    assertEquals(10, updated.getIdVehiculo());
    assertEquals(20, updated.getIdInteresado());
    assertEquals(30, updated.getIdEmpleado());
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), updated.getFechaHoraInicio());
    assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), updated.getFechaHoraFin());
    assertEquals("original", updated.getComentarios());
  }

  @Test
  void testPartialUpdate_SomeFieldsNonNull() {
    int id = 4;
    Prueba original = new Prueba();
    original.setId(id);
    original.setIdVehiculo(10);
    original.setIdInteresado(20);
    original.setIdEmpleado(30);
    original.setFechaHoraInicio(LocalDateTime.of(2023, 1, 1, 10, 0));
    original.setFechaHoraFin(LocalDateTime.of(2023, 1, 1, 12, 0));
    original.setComentarios("original");

    PruebaDTO dto = new PruebaDTO();
    dto.setIdEmpleado(77);
    dto.setComentarios("nuevo comentario");

    when(pruebaRepository.findById(id)).thenReturn(Optional.of(original));
    when(pruebaRepository.save(any(Prueba.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Prueba updated = pruebaService.partialUpdate(id, dto);
    assertEquals(10, updated.getIdVehiculo());
    assertEquals(20, updated.getIdInteresado());
    assertEquals(77, updated.getIdEmpleado());
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), updated.getFechaHoraInicio());
    assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), updated.getFechaHoraFin());
    assertEquals("nuevo comentario", updated.getComentarios());
  }

  @Test
  void testPartialUpdate_NotFound() {
    int id = 5;
    PruebaDTO dto = new PruebaDTO();
    when(pruebaRepository.findById(id)).thenReturn(Optional.empty());
    RuntimeException ex = assertThrows(RuntimeException.class, () -> pruebaService.partialUpdate(id, dto));
    assertEquals("Prueba no encontrada", ex.getMessage());
  }
}
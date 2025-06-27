package serviciopruebas.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "usuarios.service.url=http://localhost:8082/api/v1/pruebas")
class InteresadoClientTest {

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private InteresadoClient interesadoClient;

  @Test
  void testRestringirInteresado_Success() {
    doNothing().when(restTemplate).put(anyString(), isNull());
    assertDoesNotThrow(() -> interesadoClient.restringirInteresado(1));
    verify(restTemplate, times(1)).put(contains("/restringido"), isNull());
  }

  @Test
  void testRestringirInteresado_Error() {
    doThrow(new RuntimeException("fail")).when(restTemplate).put(anyString(), isNull());
    RuntimeException ex = assertThrows(RuntimeException.class, () -> interesadoClient.restringirInteresado(1));
    assertTrue(ex.getMessage().contains("Error al restringir el interesado"));
  }

  @Test
  void testInteresadoHasExpiredLicense_True() {
    when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
    assertTrue(interesadoClient.interesadoHasExpiredLicense(1));
  }

  @Test
  void testInteresadoHasExpiredLicense_False() {
    when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);
    assertFalse(interesadoClient.interesadoHasExpiredLicense(1));
  }

  @Test
  void testInteresadoHasExpiredLicense_Exception() {
    when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenThrow(new RuntimeException("fail"));
    assertFalse(interesadoClient.interesadoHasExpiredLicense(1));
  }

  @Test
  void testInteresadoIsRestricted_True() {
    when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
    assertTrue(interesadoClient.interesadoIsRestricted(1));
  }

  @Test
  void testInteresadoIsRestricted_False() {
    when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);
    assertFalse(interesadoClient.interesadoIsRestricted(1));
  }

  @Test
  void testInteresadoIsRestricted_Exception() {
    when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenThrow(new RuntimeException("fail"));
    assertFalse(interesadoClient.interesadoIsRestricted(1));
  }
}
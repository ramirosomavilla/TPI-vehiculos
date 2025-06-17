package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import serviciousuarios.services.InteresadoService;
import serviciousuarios.dtos.InteresadoDTO;
import serviciousuarios.dtos.NotificacionRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios/interesados")
@Tag(name = "Interesado", description = "CRUD operations for Interesado")
public class InteresadoController {
  @Autowired
  private InteresadoService interesadoService;

  @GetMapping
  @Operation(summary = "Get all interesados", description = "Get all interesados")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved all interesados", content = @Content(schema = @Schema(implementation = InteresadoDTO.class)))
  @ApiResponse(responseCode = "404", description = "No interesados found")
  public List<InteresadoDTO> getAllInteresados() {
    return interesadoService.getAllInteresados();
  }

  @GetMapping("/{idUsuario}/has-expired-license")
  @Operation(summary = "Verifica si la licencia del interesado está expirada", description = "Devuelve true si la licencia está expirada, false si no")
  @ApiResponse(responseCode = "200", description = "Resultado de la verificación")
  @ApiResponse(responseCode = "404", description = "Interesado no encontrado")
  public ResponseEntity<Boolean> hasExpiredLicense(@PathVariable("idUsuario") int idUsuario) {
    boolean expired = interesadoService.hasExpiredLicense(idUsuario);
    return ResponseEntity.ok(expired);
  }

  @GetMapping("/{idUsuario}/is-restricted")
  @Operation(summary = "Verifica si el interesado está restringido", description = "Devuelve true si el interesado está restringido, false si no")
  @ApiResponse(responseCode = "200", description = "Resultado de la verificación")
  @ApiResponse(responseCode = "404", description = "Interesado no encontrado")
  public ResponseEntity<Boolean> isRestricted(@PathVariable("idUsuario") int idUsuario) {
    boolean restricted = interesadoService.isRestricted(idUsuario);
    return ResponseEntity.ok(restricted);
  }

  @PutMapping("/{idUsuario}/restringido")
  @Operation(summary = "Marca al interesado como restringido", description = "Actualiza el estado del interesado a restringido = true")
  @ApiResponse(responseCode = "204", description = "Interesado restringido con exito")
  @ApiResponse(responseCode = "404", description = "Interesado no encontrado")
  public ResponseEntity<Void> restringir(@PathVariable("idUsuario") Integer idUsuario) {
      interesadoService.restringir(idUsuario);
      return ResponseEntity.noContent().build();
  }

    @PostMapping("/{idInteresado}/notificar")
    @Operation(summary = "Notifica al interesado", description = "Envía una notificación al interesado con el mensaje proporcionado")
    @ApiResponse(responseCode = "204", description = "Notificación enviada con éxito")
    @ApiResponse(responseCode = "404", description = "Interesado no encontrado")
    public ResponseEntity<Void> notificarInteresado(
            @RequestBody NotificacionRequest request) {
      interesadoService.notificarInteresado(
        request.getIdEmpleado(),
        request.getIdVehiculo(),
        request.getIdInteresado(),
        request.getTipo(),
        request.getMensaje()
      );
      return ResponseEntity.noContent().build();
    }
}

package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import serviciousuarios.services.NotificacionService;
import serviciousuarios.entities.Notificacion;
import serviciousuarios.dtos.NotificacionRequestDTO;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios/notificaciones")
@Tag(name = "Notificacion", description = "CRUD operations for Notificacion")
public class NotificacionController {
  @Autowired
  private NotificacionService notificacionService;

  @GetMapping
  @Operation(summary = "Obtener todas las notificaciones", description = "Devuelve todas las notificaciones")
  @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente", content = @Content(schema = @Schema(implementation = Notificacion.class)))
  public List<Notificacion> getAllNotificaciones() {
    return notificacionService.obtenerTodasLasNotificaciones();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener notificación por id", description = "Devuelve los datos de una notificación específica por su id.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Notificación obtenida exitosamente", content = @Content(schema = @Schema(implementation = Notificacion.class))),
      @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
  })
  public ResponseEntity<Notificacion> getNotificacionById(@PathVariable("id") int id) {
    Notificacion notif = notificacionService.getNotificacionById(id);
    if (notif == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(notif);
  }

  @PostMapping
  @Operation(summary = "Crear una nueva notificación", description = "Crea una nueva notificación en el sistema")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente", content = @Content(schema = @Schema(implementation = Notificacion.class))),
      @ApiResponse(responseCode = "400", description = "Datos inválidos")
  })
  public ResponseEntity<Notificacion> createNotificacion(@RequestBody NotificacionRequestDTO request) {
    Notificacion notif = notificacionService.createNotificacion(request);
    return ResponseEntity.status(201).body(notif);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar notificación (total)", description = "Actualiza todos los datos de una notificación por su id.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Notificación actualizada exitosamente", content = @Content(schema = @Schema(implementation = Notificacion.class))),
      @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
  })
  public ResponseEntity<Notificacion> updateNotificacion(@PathVariable("id") int id,
      @RequestBody NotificacionRequestDTO request) {
    Notificacion updated = notificacionService.updateNotificacion(id, request);
    if (updated == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(updated);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Actualizar parcialmente una notificación", description = "Actualiza parcialmente los datos de una notificación por su id.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Notificación actualizada parcialmente", content = @Content(schema = @Schema(implementation = Notificacion.class))),
      @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
  })
  public ResponseEntity<Notificacion> patchNotificacion(@PathVariable("id") int id,
      @RequestBody NotificacionRequestDTO request) {
    Notificacion patched = notificacionService.patchNotificacion(id, request);
    if (patched == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(patched);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar notificación", description = "Elimina una notificación por su id.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente"),
      @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
  })
  public ResponseEntity<Void> deleteNotificacion(@PathVariable("id") int id) {
    boolean deleted = notificacionService.deleteNotificacion(id);
    if (!deleted)
      return ResponseEntity.notFound().build();
    return ResponseEntity.noContent().build();
  }
}
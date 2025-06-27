package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import serviciousuarios.services.EmpleadoService;
import serviciousuarios.dtos.EmpleadoDTO;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios/empleados")
@Tag(name = "Empleado", description = "CRUD operations for Empleado")
public class EmpleadoController {
  @Autowired
  private EmpleadoService empleadoService;

  @GetMapping
  @Operation(summary = "Get all empleados", description = "Get all empleados")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved all empleados", content = @Content(schema = @Schema(implementation = EmpleadoDTO.class)))
  @ApiResponse(responseCode = "404", description = "No empleados found")
  public List<EmpleadoDTO> getAllEmpleados() {
    return empleadoService.getAllEmpleados();
  }

  @PostMapping
  @Operation(summary = "Crear un nuevo empleado", description = "Crea un nuevo empleado en el sistema")
  @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoDTO.class)))
  @ApiResponse(responseCode = "400", description = "Datos inválidos")
  public ResponseEntity<EmpleadoDTO> createEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
    EmpleadoDTO createdEmpleado = empleadoService.createEmpleado(empleadoDTO);
    return ResponseEntity.status(201).body(createdEmpleado);
  }

  @GetMapping("/{legajo}")
  @Operation(summary = "Obtener empleado por legajo", description = "Devuelve los datos de un empleado específico por su legajo.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Empleado obtenido exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoDTO.class))),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
  })
  public ResponseEntity<EmpleadoDTO> getEmpleadoById(@PathVariable("legajo") int legajo) {
    EmpleadoDTO empleado = empleadoService.getEmpleadoById(legajo);
    if (empleado == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(empleado);
  }

  @PutMapping("/{legajo}")
  @Operation(summary = "Actualizar empleado (total)", description = "Actualiza todos los datos de un empleado por su legajo.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoDTO.class))),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
  })
  public ResponseEntity<EmpleadoDTO> updateEmpleado(@PathVariable("legajo") int legajo,
      @RequestBody EmpleadoDTO empleadoDTO) {
    EmpleadoDTO updated = empleadoService.updateEmpleado(legajo, empleadoDTO);
    if (updated == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(updated);
  }

  @PatchMapping("/{legajo}")
  @Operation(summary = "Actualizar parcialmente un empleado", description = "Actualiza parcialmente los datos de un empleado por su legajo.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Empleado actualizado parcialmente", content = @Content(schema = @Schema(implementation = EmpleadoDTO.class))),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
  })
  public ResponseEntity<EmpleadoDTO> patchEmpleado(@PathVariable("legajo") int legajo,
      @RequestBody EmpleadoDTO empleadoDTO) {
    EmpleadoDTO patched = empleadoService.patchEmpleado(legajo, empleadoDTO);
    if (patched == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(patched);
  }

  @DeleteMapping("/{legajo}")
  @Operation(summary = "Eliminar empleado", description = "Elimina un empleado por su legajo.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
  })
  public ResponseEntity<Void> deleteEmpleado(@PathVariable("legajo") int legajo) {
    boolean deleted = empleadoService.deleteEmpleado(legajo);
    if (!deleted)
      return ResponseEntity.notFound().build();
    return ResponseEntity.noContent().build();
  }
}
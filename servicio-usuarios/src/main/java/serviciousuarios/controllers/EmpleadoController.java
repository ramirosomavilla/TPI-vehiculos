package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import serviciousuarios.services.InteresadoService;
import serviciousuarios.dtos.InteresadoDTO;
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
}

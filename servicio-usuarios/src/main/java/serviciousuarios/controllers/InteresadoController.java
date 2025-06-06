package serviciousuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import serviciousuarios.services.InteresadoService;
import serviciousuarios.dtos.InteresadoDTO;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios/interesados")
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
}

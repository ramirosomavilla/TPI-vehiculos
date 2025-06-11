package serviciopruebas.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import serviciopruebas.dtos.PruebaDTO;
import serviciopruebas.services.PruebaService;

@RestController
@RequestMapping("/api/v1/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;

    private static final Logger logger = LoggerFactory.getLogger(PruebaController.class);

    @PostMapping
    @Operation(summary = "Create a new test")
    @ApiResponse(responseCode = "201", description = "Test created successfully", content = @Content(schema = @Schema(implementation = PruebaDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<PruebaDTO> create(@RequestBody() PruebaDTO pruebaDTO) {
        PruebaDTO createdPrueba = pruebaService.create(pruebaDTO);
        return ResponseEntity.ok(createdPrueba);
    }

    @GetMapping
    public String test() {
        logger.info("¡Petición GET recibida en /pruebas!");
        return "Servicio de pruebas activo";
    }

    @GetMapping("/en-curso")
    public ResponseEntity<List<PruebaDTO>> getPruebasEnCurso() {
        List<PruebaDTO> pruebasEnCurso = pruebaService.getPruebasEnCurso();
        return ResponseEntity.ok(pruebasEnCurso);
    }
} 
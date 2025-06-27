package serviciopruebas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import serviciopruebas.dtos.PruebaDTO;
import serviciopruebas.entities.Prueba;
import serviciopruebas.services.PruebaService;

@RestController
@RequestMapping("/api/v1/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;

    @PostMapping
    @Operation(summary = "Crear una nueva prueba", description = "Crea una nueva prueba de manejo con los datos proporcionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prueba creada exitosamente", content = @Content(schema = @Schema(implementation = PruebaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<PruebaDTO> create(@RequestBody() PruebaDTO pruebaDTO) {
        PruebaDTO createdPrueba = pruebaService.create(pruebaDTO);
        return ResponseEntity.ok(createdPrueba);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las pruebas", description = "Devuelve una lista de todas las pruebas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de pruebas obtenida exitosamente")
    public List<PruebaDTO> getAll() {
        return pruebaService.findAll().stream().map(this::toDTO).toList();
    }

    private PruebaDTO toDTO(Prueba prueba) {
        PruebaDTO dto = new PruebaDTO();
        dto.setIdVehiculo(prueba.getIdVehiculo());
        dto.setIdInteresado(prueba.getIdInteresado());
        dto.setIdEmpleado(prueba.getIdEmpleado());
        dto.setFechaHoraInicio(prueba.getFechaHoraInicio());
        dto.setFechaHoraFin(prueba.getFechaHoraFin());
        dto.setComentarios(prueba.getComentarios());
        return dto;
    }

    @GetMapping("/en-curso")
    @Operation(summary = "Obtener pruebas en curso", description = "Devuelve una lista de pruebas que están actualmente en curso.")
    @ApiResponse(responseCode = "200", description = "Lista de pruebas en curso obtenida exitosamente")
    public ResponseEntity<List<PruebaDTO>> getPruebasEnCurso() {
        List<PruebaDTO> pruebasEnCurso = pruebaService.getPruebasEnCurso();
        return ResponseEntity.ok(pruebasEnCurso);
    }

    @PostMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar una prueba", description = "Finaliza la prueba especificada por su ID y agrega comentarios.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prueba finalizada exitosamente", content = @Content(schema = @Schema(implementation = PruebaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Prueba no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<PruebaDTO> finalizar(@PathVariable Integer id, @RequestBody String comentarios) {
        PruebaDTO pruebaDTO = pruebaService.finalizar(id, comentarios);
        return ResponseEntity.ok(pruebaDTO);
    }

    @GetMapping("/vehiculos/{idVehiculo}/en-curso")
    @Operation(summary = "Verificar si un vehículo está en prueba", description = "Indica si el vehículo especificado tiene una prueba en curso.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado obtenido exitosamente", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Boolean> vehiculoEnPrueba(@PathVariable Integer idVehiculo) {
        Boolean vehiculoEnPrueba = pruebaService.vehiculoEnPrueba(idVehiculo);
        return ResponseEntity.ok(vehiculoEnPrueba);
    }

    @GetMapping("/vehiculos/{idVehiculo}")
    @Operation(summary = "Obtener pruebas por vehículo", description = "Devuelve una lista de pruebas asociadas a un vehículo específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pruebas obtenida exitosamente", content = @Content(schema = @Schema(implementation = PruebaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<List<PruebaDTO>> pruebasPorVehiculo(@PathVariable Integer idVehiculo) {
        List<PruebaDTO> pruebasPorVehiculo = pruebaService.pruebasPorVehiculo(idVehiculo);
        return ResponseEntity.ok(pruebasPorVehiculo);
    }

    @GetMapping("/vehiculos/{idVehiculo}/interesado")
    @Operation(summary = "Obtener interesado de vehículo en prueba", description = "Devuelve el ID del interesado asociado a la prueba en curso de un vehículo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ID de interesado obtenido exitosamente", content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Prueba en curso no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Integer> obtenerInteresadoDeVehiculoEnPrueba(@PathVariable Integer idVehiculo) {
        Prueba pruebaEnCurso = pruebaService.obtenerPruebaEnCursoByVehiculoId(idVehiculo);
        return ResponseEntity.ok(pruebaEnCurso.getIdInteresado());
    }
}
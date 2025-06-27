package serviciopruebas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.http.ResponseEntity;

import serviciopruebas.dtos.VehiculoDTO;
import serviciopruebas.dtos.PosicionDTO;
import serviciopruebas.entities.Vehiculo;
import serviciopruebas.services.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    @Operation(summary = "Obtener todos los vehículos", description = "Devuelve una lista de todos los vehículos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente")
    public Iterable<VehiculoDTO> getAll() {
        return vehiculoService.findAll().stream().map(this::toDTO).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vehículo por ID", description = "Devuelve los datos de un vehículo específico por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo obtenido exitosamente", content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public VehiculoDTO getById(@PathVariable("id") Integer id) {
        return vehiculoService.findById(id).map(this::toDTO).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo vehículo", description = "Crea un nuevo vehículo con los datos proporcionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo creado exitosamente", content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public VehiculoDTO create(@RequestBody VehiculoDTO dto) {
        Vehiculo v = toEntity(dto);
        return toDTO(vehiculoService.save(v));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vehículo", description = "Elimina el vehículo especificado por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public void delete(@PathVariable("id") Integer id) {
        vehiculoService.deleteById(id);
    }

    @PostMapping("/{id}/posicion")
    @Operation(summary = "Actualizar posición del vehículo", description = "Guarda la posición actual del vehículo especificado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posición actualizada exitosamente", content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public VehiculoDTO recibirPosicion(@PathVariable("id") Integer id, @RequestBody PosicionDTO posicion) {
        return toDTO(vehiculoService.guardarPosicion(id, posicion.getLatitud(), posicion.getLongitud(),
                posicion.getFechaHora()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vehículo", description = "Actualiza los datos de un vehículo existente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente", content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<VehiculoDTO> update(@PathVariable("id") Integer id, @RequestBody VehiculoDTO dto) {
        return vehiculoService.findById(id)
                .map(existing -> {
                    Vehiculo v = toEntity(dto);
                    v.setId(id);
                    Vehiculo updated = vehiculoService.save(v);
                    return ResponseEntity.ok(toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente vehículo", description = "Actualiza parcialmente los datos de un vehículo existente por su ID. Solo los campos no nulos serán modificados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente", content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<VehiculoDTO> partialUpdate(@PathVariable("id") Integer id, @RequestBody VehiculoDTO dto) {
        return vehiculoService.findById(id)
                .map(existing -> {
                    if (dto.getPatente() != null)
                        existing.setPatente(dto.getPatente());
                    if (dto.getIdModelo() != null)
                        existing.setIdModelo(dto.getIdModelo());
                    if (dto.getAnio() != null)
                        existing.setAnio(dto.getAnio());
                    if (dto.getLatitud() != null)
                        existing.setLatitud(dto.getLatitud());
                    if (dto.getLongitud() != null)
                        existing.setLongitud(dto.getLongitud());
                    if (dto.getFechaUbicacion() != null)
                        existing.setFechaUbicacion(dto.getFechaUbicacion());
                    Vehiculo updated = vehiculoService.save(existing);
                    return ResponseEntity.ok(toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private VehiculoDTO toDTO(Vehiculo v) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setId(v.getId());
        dto.setPatente(v.getPatente());
        dto.setIdModelo(v.getIdModelo());
        dto.setAnio(v.getAnio());
        dto.setLatitud(v.getLatitud());
        dto.setLongitud(v.getLongitud());
        dto.setFechaUbicacion(v.getFechaUbicacion());
        return dto;
    }

    private Vehiculo toEntity(VehiculoDTO dto) {
        Vehiculo v = new Vehiculo();
        v.setPatente(dto.getPatente());
        v.setIdModelo(dto.getIdModelo());
        v.setAnio(dto.getAnio());
        return v;
    }
}
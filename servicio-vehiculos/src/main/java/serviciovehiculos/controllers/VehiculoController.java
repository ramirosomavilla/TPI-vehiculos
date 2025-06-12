package serviciovehiculos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import serviciovehiculos.dtos.VehiculoDTO;
import serviciovehiculos.entities.Vehiculo;
import serviciovehiculos.services.VehiculoService;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public Iterable<VehiculoDTO> getAll() {
        return vehiculoService.findAll().stream().map(this::toDTO).toList();
    }

    @GetMapping("/api/v1/vehiculos/{id}")
    public VehiculoDTO getById(@PathVariable("id") Integer id) {
        return vehiculoService.findById(id).map(this::toDTO).orElse(null);
    }

    @PostMapping
    public VehiculoDTO create(@RequestBody VehiculoDTO dto) {
        Vehiculo v = toEntity(dto);
        return toDTO(vehiculoService.save(v));
    }

    @DeleteMapping("/api/v1/vehiculos/{id}")
    public void delete(@PathVariable("id") Integer id) {
        vehiculoService.deleteById(id);
    }

    @PostMapping("/{id}/posicion")
    public VehiculoDTO recibirPosicion(@PathVariable("id") Integer id, @RequestBody VehiculoDTO dto) {
        return toDTO(vehiculoService.guardarPosicion(id, dto.getLatitud(), dto.getLongitud(), dto.getFechaUbicacion()));
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
        v.setId(dto.getId());
        v.setPatente(dto.getPatente());
        v.setIdModelo(dto.getIdModelo());
        v.setAnio(dto.getAnio());
        return v;
    }
} 
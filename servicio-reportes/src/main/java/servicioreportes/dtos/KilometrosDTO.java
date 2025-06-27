package servicioreportes.dtos;

import lombok.Data;

@Data
public class KilometrosDTO {
    private Integer vehiculoId;
    private double kilometrosRecorridos;

    public KilometrosDTO(Integer vehiculoId, double kilometrosRecorridos) {
        this.vehiculoId = vehiculoId;
        this.kilometrosRecorridos = kilometrosRecorridos;
    }
}

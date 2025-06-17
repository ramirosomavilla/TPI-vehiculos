package serviciopruebas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonaPeligrosaDTO {
    private CoordenadaDTO centro;
    private double radio;
}

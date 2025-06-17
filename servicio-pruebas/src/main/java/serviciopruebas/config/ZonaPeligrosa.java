package serviciopruebas.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonaPeligrosa {
    private Coordenada centro;
    private double radio;
}

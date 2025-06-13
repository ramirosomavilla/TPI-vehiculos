package serviciovehiculos.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonaPeligrosa {
    private Coordenada puntoNO;
    private Coordenada puntoSE;
}

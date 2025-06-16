package serviciopruebas.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyConfig {
    private double latitud;
    private double longitud;
    private double radio;
    private List<ZonaPeligrosa> zonasPeligrosas;
}

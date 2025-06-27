package serviciopruebas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class AgencyConfigDTO {
    @JsonProperty("ubicacion_agencia")
    private Ubicacion ubicacionAgencia;

    @JsonProperty("radio_maximo_metros")
    private int radioMaximoMetros;

    @JsonProperty("zonas_peligrosas")
    private List<ZonaPeligrosa> zonasPeligrosas;

    @Data
    public static class Ubicacion {
        @JsonProperty("latitud")
        private double latitud;

        @JsonProperty("longitud")
        private double longitud;
    }

    @Data
    public static class ZonaPeligrosa {
        @JsonProperty("id_zona")
        private String idZona;

        @JsonProperty("nombre_zona")
        private String nombreZona;

        @JsonProperty("coordenadas")
        private Ubicacion coordenadas;

        @JsonProperty("radio_metros")
        private int radioMetros;
    }
}

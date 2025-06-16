package serviciopruebas.dtos;

import java.time.LocalDateTime;

public class VehiculoDTO {
    private Integer id;
    private String patente;
    private Integer idModelo;
    private Integer anio;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaUbicacion;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public Integer getIdModelo() { return idModelo; }
    public void setIdModelo(Integer idModelo) { this.idModelo = idModelo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public LocalDateTime getFechaUbicacion() { return fechaUbicacion; }
    public void setFechaUbicacion(LocalDateTime fechaUbicacion) { this.fechaUbicacion = fechaUbicacion; }
} 
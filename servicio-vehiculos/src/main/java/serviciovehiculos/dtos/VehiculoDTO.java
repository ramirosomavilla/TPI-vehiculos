package serviciovehiculos.dtos;

public class VehiculoDTO {
    private Integer id;
    private String patente;
    private Integer idModelo;
    private Integer anio;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public Integer getIdModelo() { return idModelo; }
    public void setIdModelo(Integer idModelo) { this.idModelo = idModelo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
} 
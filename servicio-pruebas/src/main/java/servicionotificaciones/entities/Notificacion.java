@Entity
@Table(name = "Notificaciones")
@Data
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer idCliente;

    @Column(nullable = false)
    private Integer idVehiculo;

    @Column(nullable = false)
    private Integer idInteresado;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private boolean leida;

    @Column(nullable = false)
    private String mensaje;

    public Notificacion(Integer idCliente, Integer idVehiculo, Integer idInteresado, String mensaje) {
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.idInteresado = idInteresado;
        this.tipo = tipo;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
        this.mensaje = mensaje;
    }

    public Notificacion() {}
}
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByTipoAndLeidaFalse(String tipo);
    List<Notificacion> findByTipo(String tipo);
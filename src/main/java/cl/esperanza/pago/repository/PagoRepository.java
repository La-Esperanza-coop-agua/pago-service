package cl.esperanza.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import cl.esperanza.pago.model.Pago;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByRunSocio(String runSocio);
    @Query("SELECT COALESCE(SUM(p.monto), 0.0) FROM Pago p WHERE p.estado = 'PAGADO'")
    double sumTotalRecaudado();
}
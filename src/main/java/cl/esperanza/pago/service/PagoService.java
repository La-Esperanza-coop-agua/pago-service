package cl.esperanza.pago.service;

import org.springframework.stereotype.Service;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.repository.PagoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository pagoRepo;

    public PagoService(PagoRepository pagoRepo) {
        this.pagoRepo = pagoRepo;
    }

    public List<Pago> obtenerPagosPorRun(String run) {
        return pagoRepo.findByRunSocio(run);
    }

    public Pago emitirBoleta(Pago pago) {
        pago.setEstado("PENDIENTE");
        return pagoRepo.save(pago);
    }

    public Pago pagarBoleta(Integer idPago) {
        Optional<Pago> pagoOpcional = pagoRepo.findById(idPago);
        
        if (pagoOpcional.isPresent()) {
            Pago pago = pagoOpcional.get();
            pago.setEstado("PAGADO");
            return pagoRepo.save(pago);
        }
        return null;
    }
}
package cl.esperanza.pago.service;

import org.springframework.stereotype.Service;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.repository.PagoRepository;
import cl.esperanza.pago.exception.ResourceNotFoundException;
import java.util.List;

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
        return pagoRepo.save(pago);
    }

    public Pago pagarBoleta(Integer idPago) {
        Pago pago = pagoRepo.findById(idPago)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró la boleta con el ID: " + idPago));
        
        pago.setEstado("PAGADO");
        return pagoRepo.save(pago);
    }
}
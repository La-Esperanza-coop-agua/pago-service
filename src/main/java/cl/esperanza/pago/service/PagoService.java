package cl.esperanza.pago.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.esperanza.pago.exception.ResourceNotFoundException;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.repository.PagoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepo;

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

    public Double obtenerTotalRecaudado() {
        List<Pago> todosLosPagos = pagoRepo.findAll();
        double totalRecaudado = 0.0;

        for (Pago pago : todosLosPagos) {
            if ("PAGADO".equalsIgnoreCase(pago.getEstado())) {
                totalRecaudado = totalRecaudado + pago.getMonto();
            }
        }
        return totalRecaudado;
    }
}
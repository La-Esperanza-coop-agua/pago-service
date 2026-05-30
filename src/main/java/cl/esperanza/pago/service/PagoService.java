package cl.esperanza.pago.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.repository.PagoRepository;
import cl.esperanza.pago.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepo;
    private final WebClient facturacionWebClient; // <-- Agregamos el cliente HTTP

    // Inyectamos el repositorio y nuestro Bean configurado en WebClientConfig
    public PagoService(PagoRepository pagoRepo, WebClient facturacionWebClient) {
        this.pagoRepo = pagoRepo;
        this.facturacionWebClient = facturacionWebClient;
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
        
        if ("PAGADO".equals(pago.getEstado())) {
            throw new RuntimeException("Esta boleta ya se encuentra pagada.");
        }

        // =======================================================================
        // ENLACE SÍNCRONO: Avisar al microservicio de Facturación (Puerto 8083)
        // =======================================================================
        try {
            // Como tu tabla Pago no tiene un "idFactura", simularemos el flujo enviando 
            // el ID de la factura que creamos anteriormente (por ejemplo, la número 2).
            Integer idFacturaAFacturar = 2; 

            facturacionWebClient.put()
                    .uri("/{id}/pagar", idFacturaAFacturar)
                    .retrieve()
                    .bodyToMono(Void.class) // No necesitamos procesar el JSON de vuelta
                    .block(); // .block() lo hace síncrono (espera a que el otro responda)

            System.out.println("¡Facturación respondió con éxito! Estado de cuenta actualizado.");

        } catch (Exception e) {
            // Si Facturación está apagada o da un error, detenemos la transacción
            throw new RuntimeException("El pago no se procesó porque el sistema de Facturación no respondió: " + e.getMessage());
        }
        // =======================================================================

        // Si la llamada HTTP no falló, se guarda el estado en tu BD local de Pagos
        pago.setEstado("PAGADO");
        return pagoRepo.save(pago);
    }
}
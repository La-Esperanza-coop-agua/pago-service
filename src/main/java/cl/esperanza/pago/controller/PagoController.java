package cl.esperanza.pago.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.service.PagoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pago")
public class PagoController {
    
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/socio/{run}")
    public ResponseEntity<List<Pago>> getPagosPorSocio(@PathVariable String run) {
        List<Pago> pagos = pagoService.obtenerPagosPorRun(run);
        return ResponseEntity.ok(pagos);
    }

    @PostMapping("/emitir")
    public ResponseEntity<Pago> emitirNuevaBoleta(@RequestBody Pago pago) {
        Pago nuevaBoleta = pagoService.emitirBoleta(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Pago> registrarPago(@PathVariable Integer id) {
        Pago pagoActualizado = pagoService.pagarBoleta(id);
        
        if (pagoActualizado != null) {
            return ResponseEntity.ok(pagoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
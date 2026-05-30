package cl.esperanza.pago.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.service.PagoService;
import cl.esperanza.pago.dto.CreatePagoRequest;

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
        return ResponseEntity.ok(pagoService.obtenerPagosPorRun(run));
    }

    @PostMapping("/emitir")
    public ResponseEntity<Pago> emitirNuevaBoleta(@Valid @RequestBody CreatePagoRequest request) {
        Pago nuevaBoleta = pagoService.emitirBoleta(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Pago> registrarPago(@PathVariable Integer id) {
        Pago pagoActualizado = pagoService.pagarBoleta(id);
        return ResponseEntity.ok(pagoActualizado);
    }

    @GetMapping("/total-recaudado")
        public ResponseEntity<Double> getTotalRecaudado() {
        return ResponseEntity.ok(pagoService.obtenerTotalRecaudado());
    }


}
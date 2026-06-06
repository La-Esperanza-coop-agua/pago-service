package cl.esperanza.pago.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.esperanza.pago.dto.CreatePagoRequest;
import cl.esperanza.pago.mapper.PagoMapper;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.service.PagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
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
        Pago nuevaBoleta = pagoService.emitirBoleta(PagoMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Pago> registrarPago(@PathVariable Integer id) {
        Pago pagoActualizado = pagoService.pagarBoleta(id);
        return ResponseEntity.ok(pagoActualizado);
    }
}
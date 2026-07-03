package cl.esperanza.pago.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.esperanza.pago.dto.CreatePagoRequest;
import cl.esperanza.pago.mapper.PagoMapper;
import cl.esperanza.pago.model.Pago;
import cl.esperanza.pago.service.PagoService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Gestión de boletas y pagos de los socios de la cooperativa")
public class PagoController {
    
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Obtener pagos por RUN", description = "Busca todo el historial de boletas de un socio en específico.")
    @GetMapping("/socio/{run}")
    public ResponseEntity<List<Pago>> getPagosPorSocio(@PathVariable String run) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorRun(run));
    }

    @Operation(summary = "Emitir nueva boleta", description = "Crea un registro de cobro para un socio.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Boleta emitida exitosamente")
    })
    @PostMapping("/emitir")
    public ResponseEntity<Pago> emitirNuevaBoleta(@Valid @RequestBody CreatePagoRequest request) {
        Pago nuevaBoleta = pagoService.emitirBoleta(PagoMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);
    }

    @Operation(summary = "Registrar un pago", description = "Cambia el estado de una boleta a PAGADO usando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta pagada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    @PutMapping("/{id}/pagar")
    public ResponseEntity<Pago> registrarPago(@PathVariable Integer id) {
        Pago pagoActualizado = pagoService.pagarBoleta(id);
        return ResponseEntity.ok(pagoActualizado);
    }

    @Operation(summary = "Obtener el total recaudado", description = "Calcula la suma de todas las boletas en estado PAGADO. Usado por el microservicio de Reportes.")
    @GetMapping("/total-recaudado")
    public ResponseEntity<Double> getTotalRecaudado() {
        Double total = pagoService.obtenerTotalRecaudado();
        return ResponseEntity.ok(total);
    }
}
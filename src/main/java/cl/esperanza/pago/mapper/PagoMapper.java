package cl.esperanza.pago.mapper;

import cl.esperanza.pago.dto.CreatePagoRequest;
import cl.esperanza.pago.model.Pago;

public class PagoMapper {
    
    public static Pago toModel(CreatePagoRequest request) {
        return new Pago(
            null,
            request.runSocio(),
            request.monto(),
            request.fechaEmision(),
            request.fechaVencimiento(),
            "PENDIENTE"
        );
    }
}
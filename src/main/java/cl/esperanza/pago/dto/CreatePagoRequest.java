package cl.esperanza.pago.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record CreatePagoRequest(
    @NotBlank(message = "El RUN del socio es obligatorio")
    String runSocio,

    @Positive(message = "El monto debe ser mayor a cero")
    double monto,

    @NotNull(message = "La fecha de emisión es obligatoria")
    LocalDate fechaEmision,

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe estar en el futuro")
    LocalDate fechaVencimiento
) {}
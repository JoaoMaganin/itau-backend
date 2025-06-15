package itau.backend.springboot.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TransacaoDTO {

    @NotNull
    @Min(0)
    private double valor;

    @NotNull
    private OffsetDateTime dataHora;

    public double getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }
}

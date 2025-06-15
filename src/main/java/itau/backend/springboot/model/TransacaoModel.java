package itau.backend.springboot.model;

import java.time.OffsetDateTime;

public class TransacaoModel {

    private double valor;
    private OffsetDateTime dataHora;

    public TransacaoModel(final double valor, final OffsetDateTime datahora) {
        this.valor = valor;
        this.dataHora = datahora;
    }

    public double getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }
}

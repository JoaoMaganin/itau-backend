package itau.backend.springboot.DTO;

import java.util.DoubleSummaryStatistics;

public class EstatisticasDTO {

    private long quantidade;
    private double soma;
    private double media;
    private double minimo;
    private double maximo;

    public EstatisticasDTO(DoubleSummaryStatistics estatisticas) {
        this.quantidade = estatisticas.getCount();
        this.soma = estatisticas.getSum();
        this.media = estatisticas.getAverage();
        this.minimo = estatisticas.getMin();
        this.maximo = estatisticas.getMax();
    }

    public long getQuantidade() {
        return quantidade;
    }

    public double getSoma() {
        return soma;
    }

    public double getMedia() {
        return media;
    }

    public double getMinimo() {
        return minimo;
    }

    public double getMaximo() {
        return maximo;
    }
}

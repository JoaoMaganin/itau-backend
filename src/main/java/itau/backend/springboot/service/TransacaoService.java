package itau.backend.springboot.service;

import itau.backend.springboot.model.TransacaoModel;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransacaoService {

    //O desafio pede para não usar BD e sim para armazenar na memória
    private final Queue<TransacaoModel> transacoes = new ConcurrentLinkedQueue<>();

    public void addTransacao(TransacaoModel transacao) {
        transacoes.add(transacao);
    }

    public void limpaTransacoes() {
        transacoes.clear();
    }

    public DoubleSummaryStatistics getEstatisticas() {
        OffsetDateTime now = OffsetDateTime.now();
        return transacoes.stream()
                //.filter(t -> t.getDataHora().isAfter(now.minusSeconds(60)))
                .mapToDouble(TransacaoModel::getValor)
                .summaryStatistics();
    }
}

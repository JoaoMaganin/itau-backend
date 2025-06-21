package itau.backend.springboot.service;

import itau.backend.springboot.model.TransacaoModel;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransacaoService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TransacaoService.class);
    //O desafio pede para não usar BD e sim para armazenar na memória
    private final Queue<TransacaoModel> transacoes = new ConcurrentLinkedQueue<>();
    private final long JANELA_SEGUNDOS = 60;

    public void addTransacao(TransacaoModel transacao) {
        log.debug("Tentando adicionar transação: {}", transacao);

        if(transacao.getValor() <= 0) {
            log.warn("Atenção: Valor da transação tem que ser maior do que zero.");
            throw new IllegalArgumentException("O valor da transação deve ser maior que zero.");
        }

        log.info("Transação adicionada com sucesso. valor: {}, dataHora: {}", transacao.getValor(), transacao.getDataHora());
        transacoes.add(transacao);
    }

    public void limpaTransacoes() {
        log.info("Iniciando exclusão de todas as transações...");
        transacoes.clear();
        log.info("Transações excluídas com sucesso");
    }

    public DoubleSummaryStatistics getEstatisticas() {
        log.debug("Calculando estatísticas para {} transações.", transacoes.size());
        OffsetDateTime now = OffsetDateTime.now();
        StopWatch stopWatch = new StopWatch(); // Cria um novo StopWatch
        stopWatch.start("calcularEstatisticas"); // Inicia o timer com um nome de tarefa

        DoubleSummaryStatistics estatisticasCalculadas = transacoes.stream()
                //.filter(t -> t.getDataHora().isAfter(now.minusSeconds(JANELA_SEGUNDOS)))
                .mapToDouble(TransacaoModel::getValor)
                .summaryStatistics();

        stopWatch.stop(); // Para o timer
        log.info("Estatísticas calculadas: Soma={}, Média={}, Quantidade={}, Tempo para calcular={}ms", estatisticasCalculadas.getSum(), estatisticasCalculadas.getAverage(), estatisticasCalculadas.getCount(), stopWatch.getTotalTimeMillis());
        return estatisticasCalculadas;
    }
}

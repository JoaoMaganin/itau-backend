package itau.backend.springboot;

import itau.backend.springboot.model.TransacaoModel;
import itau.backend.springboot.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransacaoServiceTest {

    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
    }

    // --- Teste para addTransacao ---
    @Test
    void adicionaUmaTransacaoComSucesso() {
        TransacaoModel transacao = new TransacaoModel(100.00, OffsetDateTime.now());
        transacaoService.addTransacao(transacao);

        DoubleSummaryStatistics stats = transacaoService.getEstatisticas();
        assertEquals(1, stats.getCount());
        assertEquals(100.00, stats.getSum(), 0.001);
        assertTrue(transacaoService.getEstatisticas().getCount() > 0);
    }

    @Test
    void adicionaMultiplasTransacoesComSucesso() {
        transacaoService.addTransacao(new TransacaoModel(10.00, OffsetDateTime.now()));
        transacaoService.addTransacao(new TransacaoModel(20.00, OffsetDateTime.now()));

        DoubleSummaryStatistics stats = transacaoService.getEstatisticas();
        assertEquals(2, stats.getCount());
        assertEquals(30.00, stats.getSum(), 0.001);
    }

    @Test
    void naoAdicionaTransacaoComValorNegativoOuZero() {
        TransacaoModel transacao = new TransacaoModel(-10.00, OffsetDateTime.now());

        assertThrows(Exception.class, () -> transacaoService.addTransacao(transacao));
        assertEquals(0, transacaoService.getEstatisticas().getCount(), "Nenhuma transação deveria ter sido adicionada.");
    }

    // --- Testes para limpaTransacoes ---

    @Test
    void limpaTransacoesDeUmaFilaVazia() {
        transacaoService.limpaTransacoes();
        DoubleSummaryStatistics stats = transacaoService.getEstatisticas();
        assertEquals(0, stats.getCount());
    }

    @Test
    void limpaTransacoesDeUmaFilaComElementos() {
        transacaoService.addTransacao(new TransacaoModel(10.00, OffsetDateTime.now()));
        transacaoService.limpaTransacoes();
        DoubleSummaryStatistics stats = transacaoService.getEstatisticas();
        assertEquals(0, stats.getCount());
    }

    // --- Testes para getEstatisticas ---

    @Test
    void retornaEstatisticasCorretasParaFilaVazia() {
        DoubleSummaryStatistics stats = transacaoService.getEstatisticas();
        assertEquals(0, stats.getCount());
        assertEquals(0.0, stats.getSum(), 0.001);
        assertEquals(0.0, stats.getAverage(), 0.001); // Average para contagem 0 é 0.0
        assertTrue(Double.isInfinite(stats.getMax())); // Ou -Infinity ou Double.MIN_VALUE/MAX_VALUE dependendo da JVM
        assertTrue(Double.isInfinite(stats.getMin())); // Ou +Infinity
    }

    @Test
    void retornarEstatisticasCorretasParaMultiplasTransacoes() {
        transacaoService.addTransacao(new TransacaoModel(10.00, OffsetDateTime.now()));
        transacaoService.addTransacao(new TransacaoModel(20.00, OffsetDateTime.now()));
        transacaoService.addTransacao(new TransacaoModel(30.00, OffsetDateTime.now()));

        DoubleSummaryStatistics stats = transacaoService.getEstatisticas();
        assertEquals(3, stats.getCount());
        assertEquals(60.00, stats.getSum(), 0.001);
        assertEquals(20.00, stats.getAverage(), 0.001);
        assertEquals(30.00, stats.getMax(), 0.001);
        assertEquals(10.00, stats.getMin(), 0.001);
    }
}

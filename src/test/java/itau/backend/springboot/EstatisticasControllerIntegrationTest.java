package itau.backend.springboot;

import itau.backend.springboot.model.TransacaoModel;
import itau.backend.springboot.service.TransacaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;

@SpringBootTest // 1. @SpringBootTest: Carrega o contexto completo da aplicação.
@AutoConfigureMockMvc // 2. @AutoConfigureMockMvc: Configura e injeta uma instância de MockMvc.
public class EstatisticasControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para fazer requisições HTTP simuladas

    @Autowired
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        transacaoService.limpaTransacoes(); // Limpa o service antes de cada teste
    }

    @Test
    void deveRetornarEstatisticasCorretasQuandoHaTransacoes() throws Exception {
        transacaoService.addTransacao(new TransacaoModel(10.00, OffsetDateTime.now()));
        transacaoService.addTransacao(new TransacaoModel(20.00, OffsetDateTime.now()));
        transacaoService.addTransacao(new TransacaoModel(30.00, OffsetDateTime.now()));

        mockMvc.perform(get("/estatistica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera status 200 OK
                // Verificando o conteúdo do JSON retornado
                .andExpect(jsonPath("$.soma", is(60.0)))
                .andExpect(jsonPath("$.media", is(20.0)))
                .andExpect(jsonPath("$.maximo", is(30.0)))
                .andExpect(jsonPath("$.minimo", is(10.0)))
                .andExpect(jsonPath("$.quantidade", is(3)));
    }

    @Test
    void deveRetornarEstatisticasCorretasComUmaTransacao() throws Exception {
        transacaoService.addTransacao(new TransacaoModel(50.00, OffsetDateTime.now()));

        mockMvc.perform(get("/estatistica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soma", is(50.0)))
                .andExpect(jsonPath("$.media", is(50.0)))
                .andExpect(jsonPath("$.maximo", is(50.0)))
                .andExpect(jsonPath("$.minimo", is(50.0)))
                .andExpect(jsonPath("$.quantidade", is(1)));
    }

    @Test
    void deveRetornarEstatisticasCorretasQuandoNaoHaTransacoes() throws Exception {
        mockMvc.perform(get("/estatistica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soma", is(0.0)))
                .andExpect(jsonPath("$.media", is(0.0)))
                .andExpect(jsonPath("$.maximo", is("-Infinity"))) // DoubleSummaryStatistics retorna -Infinity para max vazio
                .andExpect(jsonPath("$.minimo", is("Infinity"))) // DoubleSummaryStatistics retorna +Infinity para min vazio
                .andExpect(jsonPath("$.quantidade", is(0)));
    }

}

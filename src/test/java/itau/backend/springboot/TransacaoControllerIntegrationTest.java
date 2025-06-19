package itau.backend.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import itau.backend.springboot.DTO.TransacaoDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest // 1. @SpringBootTest: Carrega o contexto completo da aplicação.
@AutoConfigureMockMvc // 2. @AutoConfigureMockMvc: Configura e injeta uma instância de MockMvc.
public class TransacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para fazer requisições HTTP simuladas

    @Autowired
    private TransacaoService transacaoService;

    private ObjectMapper objectMapper; // Para serializar DTOs para JSON

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        transacaoService.limpaTransacoes(); // Limpa o service antes de cada teste
    }

    // --- Testes para POST /transacao ---

    @Test
    void retorna201AoAdicionarTransacaoValida() throws Exception {
        TransacaoDTO validRequest = new TransacaoDTO(100.50, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest))) // Converte o DTO para JSON
                .andExpect(status().isCreated()); // Espera status 201 Created

        assertEquals(1, transacaoService.getEstatisticas().getCount());
    }

    @Test
    void retorna422AoAdicionarTransacaoComValorZero() throws Exception {
        TransacaoDTO invalidRequest = new TransacaoDTO(0.00, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnprocessableEntity()); // Espera status 422 Unprocessable Entity

        assertEquals(0, transacaoService.getEstatisticas().getCount());
    }

    @Test
    void retorna422AoAdicionarTransacaoComValorNegativo() throws Exception {
        TransacaoDTO invalidRequest = new TransacaoDTO(-50.00, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnprocessableEntity());

        assertEquals(0, transacaoService.getEstatisticas().getCount());
    }

    // --- Testes para DELETE /transacao ---

    @Test
    void retornar200AoLimparTransacoes() throws Exception {
        transacaoService.addTransacao(new TransacaoModel(10.00, OffsetDateTime.now()));
        transacaoService.addTransacao(new TransacaoModel(20.00, OffsetDateTime.now()));
        assertEquals(2, transacaoService.getEstatisticas().getCount()); // Confirma que foram adicionadas

        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk()); // Espera status 200 OK

        assertEquals(0, transacaoService.getEstatisticas().getCount());
    }

    @Test
    void retornar200AoLimparTransacoesVazias() throws Exception {
        // @BeforeEach garante que a fila já começa vazia

        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk()); // Espera status 200 OK

        assertEquals(0, transacaoService.getEstatisticas().getCount());
    }
}

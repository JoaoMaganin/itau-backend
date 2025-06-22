package itau.backend.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itau.backend.springboot.DTO.TransacaoDTO;
import itau.backend.springboot.model.TransacaoModel;
import itau.backend.springboot.service.TransacaoService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@Tag(name = "Transações", description = "Gerenciamento de transações financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TransacaoController.class);

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Adicionar uma nova transação",
            description = "Registra uma transação financeira no sistema. O valor deve ser maior que zero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "422", description = "Dados da transação inválidos (valor <= 0)", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoDTO request) {
        log.info("Requisição POST recebido de /transacao com valores : valor: {} and dataHora: {}", request.getValor(), request.getDataHora());
        if( request.getValor() <= 0) {
            log.warn("Adição de transação impedida: valor tem que ser maior que zero : valor: {}", request.getValor());
            return ResponseEntity.unprocessableEntity().build();
        }
        transacaoService.addTransacao(new TransacaoModel(request.getValor(), request.getDataHora()));
        log.info("Transação adicionada com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Limpar todas as transações",
            description = "Remove todas as transações armazenadas em memória do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações limpas com sucesso", content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping
    public ResponseEntity<Void> limparTransacoes() {
        log.info("Requisição delete de /transacao.");
        transacaoService.limpaTransacoes();
        log.info("Transações deletadas com sucesso.");
        return ResponseEntity.ok().build();
    }
}

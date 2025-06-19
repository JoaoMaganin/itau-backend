package itau.backend.springboot.controller;

import itau.backend.springboot.DTO.TransacaoDTO;
import itau.backend.springboot.model.TransacaoModel;
import itau.backend.springboot.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private Logger log;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

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

    @DeleteMapping
    public ResponseEntity<Void> limparTransacoes() {
        log.info("Requisição delete de /transacao.");
        transacaoService.limpaTransacoes();
        log.info("Transações deletadas com sucesso.");
        return ResponseEntity.ok().build();
    }
}

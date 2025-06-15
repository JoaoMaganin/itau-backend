package itau.backend.springboot.controller;

import itau.backend.springboot.DTO.TransacaoDTO;
import itau.backend.springboot.model.TransacaoModel;
import itau.backend.springboot.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoDTO request) {
        if(request.getDataHora().isAfter(OffsetDateTime.now()) || request.getValor() <= 0) {
            return ResponseEntity.unprocessableEntity().build();
        }
        transacaoService.addTransacao(new TransacaoModel(request.getValor(), request.getDataHora()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> limparTransacoes() {
        transacaoService.limpaTransacoes();
        return ResponseEntity.ok().build();
    }
}

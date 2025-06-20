package itau.backend.springboot.controller;

import itau.backend.springboot.DTO.EstatisticasDTO;
import itau.backend.springboot.service.TransacaoService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {

    private final TransacaoService transacaoService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EstatisticasController.class);

    public EstatisticasController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<EstatisticasDTO> getEstatisticas() {
        log.info("Requisição GET recebido de /estatistica...");
        DoubleSummaryStatistics estatisticas = transacaoService.getEstatisticas();
        return ResponseEntity.ok(new EstatisticasDTO(estatisticas));
    }
}

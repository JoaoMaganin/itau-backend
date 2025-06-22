package itau.backend.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Estatisticas", description="Visualização das estatísticas financeiras")
public class EstatisticasController {

    private final TransacaoService transacaoService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EstatisticasController.class);

    public EstatisticasController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Obter estatísticas das transações",
            description = "Retorna a soma, média, máximo, mínimo e contagem das transações armazenadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstatisticasDTO.class)))
    })
    @GetMapping
    public ResponseEntity<EstatisticasDTO> getEstatisticas() {
        log.info("Requisição GET recebido de /estatistica...");
        DoubleSummaryStatistics estatisticas = transacaoService.getEstatisticas();
        return ResponseEntity.ok(new EstatisticasDTO(estatisticas));
    }
}

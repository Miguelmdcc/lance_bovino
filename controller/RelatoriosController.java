package web.lance_bovino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.lance_bovino.service.RelatoriosService;

@Controller
public class RelatoriosController {

    private static final Logger logger = LoggerFactory.getLogger(RelatoriosController.class);

    private final RelatoriosService relatoriosService;

    // Injeção de dependência via construtor (substituindo o @Autowired)
    public RelatoriosController(RelatoriosService relatoriosService) {
        this.relatoriosService = relatoriosService;
    }

    @GetMapping("/relatorios/todosleiloes")
    public ResponseEntity<byte[]> gerarRelatorioComplexoTodosLeiloes() {
        logger.debug("Gerando relatório complexo de todos os leilões com gados");

        // Você precisará criar esse método lá no seu RelatoriosService
        byte[] relatorio = relatoriosService.gerarRelatorioTodosLeiloes();

        logger.debug("Relatório gerado com sucesso em memória");
        logger.debug("Retornando o relatório de leilões e gados como PDF");
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                // O "attachment" força o navegador a fazer o download em vez de tentar abrir na aba
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Leiloes_com_Gados.pdf")
                .body(relatorio);
    }

}
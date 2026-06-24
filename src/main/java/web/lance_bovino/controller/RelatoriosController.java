package web.lance_bovino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.lance_bovino.service.RelatoriosService;

@Controller
public class RelatoriosController {

    private static final Logger logger = LoggerFactory.getLogger(RelatoriosController.class);

    @Autowired
    private RelatoriosService relatorioService;

    // @GetMapping("/relatorios/todasvacinas")
    // public ResponseEntity<Void> triggerDownload(HttpServletResponse response) {
    //     response.setHeader("HX-Redirect", "/relatorios/todasvacinasreal");
    //     return new ResponseEntity<>(HttpStatus.OK);
    // }


    @GetMapping("/relatorios/todosgados")
    public ResponseEntity<byte[]> gerarRelatorioCompletoTodosGados() {
        logger.debug("Gerando relatório completo de todos os gados");

        byte[] relatorio = relatorioService.gerarRelatorioTodosGados();

        logger.debug("Relatório completo de todos os gados");
        logger.debug("Retornando o relatório completo de todos os gados");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=GadosHistorico.pdf")
                .body(relatorio);
    }

}

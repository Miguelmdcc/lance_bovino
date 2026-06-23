package web.lance_bovino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import web.lance_bovino.service.RelatorioService;

@Controller
public class RelatoriosController {

    @Autowired
    private RelatorioService relatorioService; // Injeta o service que você acabou de criar

    @GetMapping("/gado/relatorio")
    public ResponseEntity<byte[]> baixarRelatorioGados() {
        
        byte[] relatorio = relatorioService.gerarRelatorioTodosGados();

        if (relatorio == null) {
            return ResponseEntity.badRequest().build(); // Retorna erro se falhar na geração
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Relatorio_Gado.pdf")
                .body(relatorio);
    }
}

//Colocar no gado/mostrar.html ou no gado/listar.html:
//<a th:href="@{/gado/relatorio}" target="_blank"
//class="py-2 px-4 inline-flex items-center gap-x-2 text-sm font-medium rounded-lg border border-transparent bg-green-600 text-white hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500">
// <svg class="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
//   <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
//</svg>
// Gerar Relatório de Gado (PDF)
//</a>

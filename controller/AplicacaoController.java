package web.lance_bovino.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Importações baseadas na estrutura que criamos
import web.lance_bovino.dto.LeilaoDTOInput; // Você precisará criar este DTO depois
import web.lance_bovino.model.Gado;
import web.lance_bovino.service.GadoService;
import web.lance_bovino.service.LeilaoService;

@Controller
public class LeilaoController {

    private static final Logger logger = LoggerFactory.getLogger(LeilaoController.class);

    private final LeilaoService leilaoService;
    private final GadoService gadoService;

    // Injeção de dependências via construtor (Boa prática!)
    public LeilaoController(LeilaoService leilaoService, GadoService gadoService) {
        this.leilaoService = leilaoService;
        this.gadoService = gadoService;
    }

    @GetMapping("/leilao/cadastrar")
    public String abrirCadastro(LeilaoDTOInput dto) {
        // Retorna apenas o fragmento do formulário via HTMX
        return "leiloes/cadastrar :: formulario";
    }

    @GetMapping("/leilao/pesquisargado")
    public String pesquisarGado(
            @RequestParam(value = "gadoBusca", required = false) String gadoBusca,
            @RequestParam(value = "gadoFilterBusca", required = false) String gadoFilterBusca,
            Model model) {
            
        // Permite o uso tanto na tela de cadastro quanto na tela de alteração
        String busca = gadoBusca != null ? gadoBusca : gadoFilterBusca;
        List<Gado> gados = new ArrayList<>();
        
        if (busca != null) {
            // Supondo que você criará um método no GadoService para buscar por nome ou brinco
            gados = gadoService.buscarPorNome(busca);
            logger.debug("Gados buscados: {}", gados);
        }
        
        model.addAttribute("gados", gados);
        
        // Retorna uma lista de gados (provavelmente uma <tr> de tabela ou <option> de select)
        return "gados/listar :: lista";
    }
}
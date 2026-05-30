package web.lance_bovino.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

// Ajuste os imports para os pacotes corretos do seu projeto
import web.lance_bovino.dto.LeilaoDTOInput;
import web.lance_bovino.filter.LeilaoFilter;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.Gado;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.pagination.PageWrapper;
import web.lance_bovino.service.LeilaoService;
import web.lance_bovino.service.GadoService;

@Controller
@RequestMapping("/leilao")
public class LeilaoController {

    private static final Logger logger = LoggerFactory.getLogger(LeilaoController.class);

    private final LeilaoService leilaoService;
    private final GadoService gadoService;

    public LeilaoController(LeilaoService leilaoService, GadoService gadoService) {
        this.leilaoService = leilaoService;
        this.gadoService = gadoService;
    }

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisa() {
        return "leiloes/pesquisar :: formulario";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(LeilaoFilter filtro, Model model,
                            @PageableDefault(size = 10) 
                            // O banco irá ordenar pelo 'id' do Leilão
                            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, 
                            HttpServletRequest request) {
        Page<Leilao> pagina = leilaoService.pesquisar(filtro, pageable);
        logger.info("Leilões pesquisados: {}", pagina.getContent());
        
        PageWrapper<Leilao> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        
        return "leiloes/mostrar :: tabela";
    }

    @GetMapping("/cadastrar")
    public String abrirCadastrar(LeilaoDTOInput dto) {
        return "leiloes/cadastrar :: formulario";
    }

    // Método adaptado para buscar o Gado ao invés da Vacina no momento do cadastro do Leilão
    @GetMapping("/pesquisargado")
    public String pesquisarGado(@RequestParam(value = "gadoBusca", required = false) String gadoBusca,
            @RequestParam(value = "gadoFilterBusca", required = false) String gadoFilterBusca, Model model) {
            
        // Pode ser usado no cadastro ou na alteracao
        String busca = gadoBusca != null ? gadoBusca : gadoFilterBusca;
        List<Gado> gados = new ArrayList<>();
        
        if (busca != null) {
            // Supondo que você crie este método no GadoService para buscar pelo nome ou brinco
            gados = gadoService.pesquisarGeral(busca);
            logger.debug("Gados buscados: {}", gados);
        }
        
        model.addAttribute("gados", gados);
        return "gados/listar :: lista";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid LeilaoDTOInput dto, BindingResult resultado, RedirectAttributes atributo) {
        if (resultado.hasErrors()) {
            logger.info("O leilão recebido para cadastrar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "leiloes/cadastrar :: formulario";
        } else {
            leilaoService.salvar(dto.toLeilao());
            atributo.addFlashAttribute("notificacao", new NotificacaoSweetAlert2("Leilão cadastrado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/leilao/cadastrar";
        }
    }

    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable("id") Long id, Model model) {
        LeilaoDTOInput dto = LeilaoDTOInput.fromLeilao(leilaoService.buscar(id));
        if (dto != null) {
            model.addAttribute("leilaoDTOInput", dto);
            return "leiloes/alterar :: formulario";
        } else {
            model.addAttribute("mensagem", "Não foi encontrado um leilão com esse ID");
            return "mensagem :: texto";
        }
    }

    @PostMapping("/alterar")
    public String alterar(@Valid LeilaoDTOInput dto, BindingResult resultado, RedirectAttributes atributo) {
        if (resultado.hasErrors()) {
            logger.info("O leilão recebido para alterar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "leiloes/alterar :: formulario";
        } else {
            leilaoService.alterar(dto.toLeilao());
            atributo.addFlashAttribute("notificacao", new NotificacaoSweetAlert2("Leilão alterado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/leilao/abrirpesquisar";
        }
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes atributo) {
        leilaoService.remover(id);
        atributo.addFlashAttribute("notificacao", new NotificacaoSweetAlert2("Leilão removido com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "redirect:/leilao/abrirpesquisar";
    }

}
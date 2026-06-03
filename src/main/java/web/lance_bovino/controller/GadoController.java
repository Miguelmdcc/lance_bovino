package web.lance_bovino.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.lance_bovino.dto.GadoDTOInput;
import web.lance_bovino.filter.GadoFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Status;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.pagination.PageWrapper;
import web.lance_bovino.service.GadoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class GadoController {

    private static final Logger logger = LoggerFactory.getLogger(GadoController.class);

    private GadoService gadoService;

    public GadoController(GadoService gadoService) {
        this.gadoService = gadoService;
    }

    @GetMapping("/gado/abrirpesquisar")
    public String abrirPesquisa() {
        logger.debug("Entrandi na pesquisa");
        return "gado/pesquisar :: formulario";
    }

    @GetMapping("/gado/pesquisar")
    public String pesquisar(GadoFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Gado> pagina = gadoService.pesquisar(filtro, pageable);
        logger.info("Gados pesquisados: {}", pagina.getContent());
        PageWrapper<Gado> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "gado/mostrar :: tabela";
    }

    @GetMapping("/gado/cadastrar")
    public String abrirCadastro(GadoDTOInput dto) {
        return "gado/cadastrar :: formulario";
    }

    @PostMapping("/gado/cadastrar")
    public String cadastrar(@Valid GadoDTOInput dto, BindingResult resultado,
            RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            logger.info("O gado recebido para cadastrar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "gado/cadastrar :: formulario";
        } else {
            gadoService.salvar(dto.toGado());
            atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Gado cadastrado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/gado/cadastrar";
        }
    }

    @GetMapping("/gado/alterar/{codigo}")
    public String abrirAlterar(@PathVariable Long codigo, Model model) {
        GadoDTOInput dto = GadoDTOInput.fromGado(gadoService.buscar(codigo));
        model.addAttribute("gadoDTOInput", dto);
        return "gado/alterar :: formulario";
    }

    @PostMapping("/gado/alterar")
    public String alterar(@Valid GadoDTOInput dto, BindingResult resultado,
            RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            logger.info("O gado recebido para alterar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "gado/alterar :: formulario";
        } else {
            gadoService.atualizar(dto.toGado());
            atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2("Gado alterado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/gado/abrirpesquisar";
        }
    }

    @GetMapping("/mensagem")
    public String mostrarMensagem() {
        // Retorna o arquivo mensagem.html que está na raiz da pasta templates
        return "mensagem"; 
    }

    @GetMapping("/gado/remover/{codigo}")
    public String remover(@PathVariable Long codigo, RedirectAttributes atributos) {
        Gado gado = gadoService.buscar(codigo);
        if (gado != null) {
            gado.setStatus(Status.INATIVO);
            gadoService.atualizar(gado);
            atributos.addFlashAttribute("mensagem", "Gado removido com sucesso");
        } else {
            atributos.addFlashAttribute("mensagem",
                    "Não foi encontrado um gado com esse codigo");
        }
        return "redirect:/mensagem";
    }
}

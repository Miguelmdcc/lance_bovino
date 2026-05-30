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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

// Ajuste os imports para a sua estrutura
import web.lance_bovino.dto.UsuarioDTOInput;
import web.lance_bovino.filter.UsuarioFilter;
import web.lance_bovino.model.User;
import web.lance_bovino.model.Status;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.pagination.PageWrapper;
import web.lance_bovino.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    // Injeção de dependência recomendada por construtor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisa() {
        return "usuarios/pesquisar :: formulario";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(UsuarioFilter filtro, Model model,
            // A ordenação é por "codigo" pois é assim que está na entidade User.java
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
            
        Page<User> pagina = usuarioService.pesquisar(filtro, pageable);
        logger.info("Usuários pesquisados: {}", pagina.getContent());
        
        PageWrapper<User> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        
        return "usuarios/mostrar :: tabela";
    }

    @GetMapping("/cadastrar")
    public String abrirCadastro(UsuarioDTOInput dto) {
        return "usuarios/cadastrar :: formulario";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid UsuarioDTOInput dto, BindingResult resultado,
            RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            logger.info("O usuário recebido para cadastrar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "usuarios/cadastrar :: formulario";
        } else {
            usuarioService.salvar(dto.toUser());
            atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Usuário cadastrado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/usuario/cadastrar";
        }
    }

    @GetMapping("/alterar/{codigo}")
    public String abrirAlterar(@PathVariable Long codigo, Model model) {
        UsuarioDTOInput dto = UsuarioDTOInput.fromUser(usuarioService.buscar(codigo));
        model.addAttribute("usuarioDTOInput", dto);
        return "usuarios/alterar :: formulario";
    }

    @PostMapping("/alterar")
    public String alterar(@Valid UsuarioDTOInput dto, BindingResult resultado,
            RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            logger.info("O usuário recebido para alterar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "usuarios/alterar :: formulario";
        } else {
            usuarioService.atualizar(dto.toUser());
            atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Usuário alterado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/usuario/abrirpesquisar";
        }
    }

    // Método corrigido para utilizar o padrão SweetAlert2 e não quebrar o layout
    @GetMapping("/remover/{codigo}")
    public String remover(@PathVariable Long codigo, RedirectAttributes atributos) {
        User usuario = usuarioService.buscar(codigo);
        if (usuario != null) {
            // "Exclusão Lógica" - Apenas muda o status para INATIVO em vez de apagar do banco
            usuario.setStatus(Status.INATIVO);
            usuarioService.atualizar(usuario);
            atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Usuário removido com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
        } else {
            atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Não foi encontrado um usuário com esse código.", TipoNotificaoSweetAlert2.ERROR, 4000));
        }
        return "redirect:/usuario/abrirpesquisar";
    }
}
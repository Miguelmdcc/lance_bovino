package web.lance_bovino.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import web.lance_bovino.dto.GadoDTOInput;
import web.lance_bovino.filter.GadoFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.GadoHistory;
import web.lance_bovino.model.Status;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.pagination.PageWrapper;
import web.lance_bovino.repository.GadoRepository;
import web.lance_bovino.repository.UsuarioRepository;
import web.lance_bovino.service.GadoHistoryService;
import web.lance_bovino.service.GadoService;


@Controller
@RequestMapping("/gado")
public class GadoController {

	private static final Logger logger = LoggerFactory.getLogger(GadoController.class);
	
	private GadoService gadoService;
    private UsuarioRepository usuarioRepository;
	private GadoHistoryService gadoHistoryService;
	
	public GadoController(GadoService gadoService, UsuarioRepository usuarioRepository
		,GadoHistoryService gadoHistoryService) {
		this.gadoService = gadoService;
        this.usuarioRepository = usuarioRepository;
		this.gadoHistoryService = gadoHistoryService;
	}

	@GetMapping("/abrirpesquisar")
    public String abrirPesquisa(Model model) {
        return "gado/pesquisar :: formulario";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(GadoFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails) {
		Long usuarioCodigo = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        Page<Gado> pagina = gadoService.pesquisar(filtro, pageable, usuarioCodigo);
        logger.info("Gados do usuario {} pesquisados: {}", userDetails.getUsername(), pagina.getContent());
        PageWrapper<Gado> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "gado/mostrar :: tabela";
    }

	@GetMapping("/abrirpesquisarrelatorio")
    public String abrirPesquisaRelatorio(Model model) {
        return "gado/pesquisar_relatorio :: formulario";
    }

    @GetMapping("/pesquisarrelatorio")
    public String pesquisarRelatorio(GadoFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Gado> pagina = gadoService.pesquisarTodos(filtro, pageable);
        PageWrapper<Gado> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "gado/mostrar_relatorio :: tabela";
    }

	@GetMapping("/cadastrar")
	public String abrirCadastroGado(GadoDTOInput gado, Model model) {
		return "gado/cadastrar :: formulario";
	}
	
	@PostMapping("/cadastrar")
	public String cadastrarNovoGado(@Valid GadoDTOInput gado, 
        BindingResult resultado, Model model, 
        RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails) {
		if (resultado.hasErrors()) {
			logger.info("O gado recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			return "gado/cadastrar :: formulario";
		} else {
			gado.setStatus(Status.ATIVO);
            Usuario usuario = usuarioRepository.findByNome(userDetails.getUsername());
            if(usuario == null){
                logger.info("Usuario com nome {} não encontrado", userDetails.getUsername());
                redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Usuário com o nome"+userDetails.getUsername(),
                    TipoNotificaoSweetAlert2.ERROR, 4000));
                return "gado/cadastrar :: formulario";
            }
            gado.setCodigoUsuario(usuario.getCodigo());;
			Gado gado_model = gado.toGado();
			gadoService.salvar(gado_model);
			GadoHistory gadoHistory = new GadoHistory();
			gadoHistory.setGado(gado_model);
			gadoHistory.setUsuario(usuario);
			gadoHistory.setAtivo(true);
			gadoHistory.setTimestampDeCriacao(LocalDateTime.now());
			gadoHistoryService.salvar(gadoHistory);
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Cadastro de gado efetuado com sucesso.",
                    TipoNotificaoSweetAlert2.SUCCESS, 4000));
			return "redirect:/gado/cadastrar";
		}
	}

	@PostMapping("/alterar")
	public String alterarGado(@Valid GadoDTOInput gado, BindingResult resultado, 
		RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (resultado.hasErrors()) {
			logger.info("Algum dado do gado recebido para alterar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			return "gado/alterar :: formulario";
		} else {
			gado.setStatus(Status.ATIVO);
			gadoService.atualizar(gado.toGado());
			redirectAttributes.addAttribute("codigo",gado.getCodigo());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Dados do gado alterados com sucesso.",
			TipoNotificaoSweetAlert2.SUCCESS, 4000));
			
			return "redirect:/gado/alterar/{codigo}";
		}
	}
    
	@GetMapping("/alterar/{codigo}")
    public String abrirAlterar(@PathVariable Long codigo, Model model) {
        GadoDTOInput dto = GadoDTOInput.fromGado(gadoService.buscar(codigo));
        model.addAttribute("gadoDTOInput", dto);
        return "gado/alterar :: formulario";
    }

	@GetMapping("/remover/{codigo}")
    public String remover(@PathVariable Long codigo, RedirectAttributes atributos,@AuthenticationPrincipal UserDetails userDetails) {
        Gado gado = gadoService.buscar(codigo);
        if (gado != null) {
			gado.setStatus(Status.INATIVO);
			gadoService.atualizar(gado);
			Usuario usuario = usuarioRepository.findByNome(userDetails.getUsername());
			GadoHistory gadoHistory = new GadoHistory();
			gadoHistory.setGado(gado);
			gadoHistory.setUsuario(usuario);
			gadoHistory.setAtivo(false);
			gadoHistory.setTimestampDeCriacao(LocalDateTime.now());
			gadoHistoryService.salvar(gadoHistory);
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Gado removido com sucesso.",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        } else {
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Não foi encontrado um gado com esse codigo",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        }
        return "redirect:/gado/pesquisar";
    }
	
}

package web.lance_bovino.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import web.lance_bovino.dto.LeilaoDTOInput;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.repository.UsuarioRepository;
import web.lance_bovino.service.GadoService;


@Controller
@RequestMapping("/leilao")
public class LeilaoController {

	private static final Logger logger = LoggerFactory.getLogger(LeilaoController.class);

    private UsuarioRepository usuarioRepository;
	private GadoService gadoService;
	
	public LeilaoController(UsuarioRepository usuarioRepository, GadoService gadoService) {
        this.usuarioRepository = usuarioRepository;
		this.gadoService = gadoService;
	}

	// @GetMapping("/abrirpesquisar")
    // public String abrirPesquisa(Model model) {
    //     return "gado/pesquisar :: formulario";
    // }

    // @GetMapping("/pesquisar")
    // public String pesquisar(GadoFilter filtro, Model model,
    //         @PageableDefault(size = 9) @SortDefault(sort = "codigo",
    //                 direction = Sort.Direction.ASC) Pageable pageable,
    //         HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails) {
	// 	Long usuarioCodigo = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
    //     Page<Gado> pagina = gadoService.pesquisar(filtro, pageable, usuarioCodigo);
    //     logger.info("Gados do usuario {} pesquisados: {}", userDetails.getUsername(), pagina.getContent());
    //     PageWrapper<Gado> paginaWrapper = new PageWrapper<>(pagina, request);
    //     model.addAttribute("pagina", paginaWrapper);
    //     return "gado/mostrar :: tabela";
    // }

	@GetMapping("/cadastrar")
	public String abrirCadastroLeilao(LeilaoDTOInput leilao, Model model) {
		return "leilao/cadastrar :: formulario";
	}

    @GetMapping("/pesquisargado")
    public String pesquisarGado(String gadoBusca, Model model) {
        List<Gado> gados = gadoService.pesquisarGeral(gadoBusca);
        logger.debug("Gados buscados: {}", gados);
        model.addAttribute("gados", gados);
        return "gado/listar :: lista";
    }
	
	@PostMapping("/cadastrar")
	public String cadastrarNovoLeilao(@Valid LeilaoDTOInput leilao, 
        BindingResult resultado, Model model, 
        RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails) {
		if (resultado.hasErrors()) {
			logger.info("O leilao recebido para cadastrar não é válido.");			
			if (resultado.hasFieldErrors("finalDateTime") 
					&& !resultado.hasFieldErrors("dataFinal") 
					&& !resultado.hasFieldErrors("horaFinal")) {
				
				resultado.rejectValue("dataFinal", "error.dataFinal", 
					resultado.getFieldError("finalDateTime").getDefaultMessage());
			}

			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			return "leilao/cadastrar :: formulario";
		} else {
			leilao.setStatus(StatusLeilao.AGUARDANDO);
            Usuario usuario = usuarioRepository.findByNome(userDetails.getUsername());
            if(usuario == null){
                logger.info("Usuario com nome {} não encontrado", userDetails.getUsername());
                redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Usuário com o nome"+userDetails.getUsername(),
                    TipoNotificaoSweetAlert2.ERROR, 4000));
                return "gado/cadastrar :: formulario";
            }
			leilao.salvar(leilao.toLeilao());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Cadastro de gado efetuado com sucesso.",
                    TipoNotificaoSweetAlert2.SUCCESS, 4000));
			return "redirect:/leilao/cadastrar";
		}
	}

	// @PostMapping("/alterar")
	// public String alterarGado(@Valid GadoDTOInput gado, BindingResult resultado, 
	// 	RedirectAttributes redirectAttributes, HttpServletResponse response) {
	// 	if (resultado.hasErrors()) {
	// 		logger.info("Algum dado do gado recebido para alterar não é válido.");
	// 		logger.info("Erros encontrados:");
	// 		for (FieldError erro : resultado.getFieldErrors()) {
	// 			logger.info("{}", erro);
	// 		}
	// 		return "gado/alterar :: formulario";
	// 	} else {
	// 		gado.setStatus(Status.ATIVO);
	// 		gadoService.atualizar(gado.toGado());
	// 		redirectAttributes.addAttribute("codigo",gado.getCodigo());
	// 		redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Dados do gado alterados com sucesso.",
	// 		TipoNotificaoSweetAlert2.SUCCESS, 4000));
			
	// 		return "redirect:/gado/alterar/{codigo}";
	// 	}
	// }
    
	// @GetMapping("/alterar/{codigo}")
    // public String abrirAlterar(@PathVariable Long codigo, Model model) {
    //     GadoDTOInput dto = GadoDTOInput.fromGado(gadoService.buscar(codigo));
    //     model.addAttribute("gadoDTOInput", dto);
    //     return "gado/alterar :: formulario";
    // }

	// @GetMapping("/remover/{codigo}")
    // public String remover(@PathVariable Long codigo, RedirectAttributes atributos) {
    //     Gado gado = gadoService.buscar(codigo);
    //     if (gado != null) {
	// 		gado.setStatus(Status.INATIVO);
	// 		gadoService.atualizar(gado);
	// 		atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Gado removido com sucesso.",
	// 			TipoNotificaoSweetAlert2.SUCCESS, 4000));
    //     } else {
	// 		atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Não foi encontrado um gado com esse codigo",
	// 			TipoNotificaoSweetAlert2.SUCCESS, 4000));
    //     }
    //     return "redirect:/gado/pesquisar";
    // }
	
}

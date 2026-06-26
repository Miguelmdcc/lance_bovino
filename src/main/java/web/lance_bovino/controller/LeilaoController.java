package web.lance_bovino.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import web.lance_bovino.dto.LeilaoDTOInput;
import web.lance_bovino.filter.LeilaoBidHistoryFilter;
import web.lance_bovino.filter.LeilaoFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.LeilaoBidHistory;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.pagination.PageWrapper;
import web.lance_bovino.repository.UsuarioRepository;
import web.lance_bovino.service.GadoService;
import web.lance_bovino.service.LeilaoBidHistoryService;
import web.lance_bovino.service.LeilaoService;


@Controller
@RequestMapping("/leilao")
public class LeilaoController {

	private static final Logger logger = LoggerFactory.getLogger(LeilaoController.class);

    private UsuarioRepository usuarioRepository;
	private GadoService gadoService;
	private LeilaoService leilaoService;
	private LeilaoBidHistoryService leilaoBidHistoryService;
	
	public LeilaoController(UsuarioRepository usuarioRepository, GadoService gadoService,
		LeilaoService leilaoService, LeilaoBidHistoryService leilaoBidHistoryService
	) {
        this.usuarioRepository = usuarioRepository;
		this.gadoService = gadoService;
		this.leilaoService = leilaoService;
		this.leilaoBidHistoryService = leilaoBidHistoryService;
	}

	@GetMapping("/abrirpesquisarmeusleiloes")
    public String abrirPesquisaMeusLeiloes(String gadoBusca, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		Long codigo_usuario = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        List<Gado> gados = gadoService.pesquisarGeral(gadoBusca, codigo_usuario);
        logger.debug("Gados buscados: {}", gados);
        model.addAttribute("gados", gados);
		model.addAttribute("leilaoDTOInput", new LeilaoDTOInput());
		model.addAttribute("statuses",StatusLeilao.values());
        return "leilao/pesquisar_meus_leiloes :: formulario";
    }

    @GetMapping("/pesquisarmeusleiloes")
    public String pesquisarMeusLeiloes(LeilaoFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails) {
		Long usuarioCodigo = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        Page<Leilao> pagina = leilaoService.pesquisarUsuario(filtro, pageable, usuarioCodigo);
        logger.info("Leiloes do usuario {} pesquisados: {}", userDetails.getUsername(), pagina.getContent());
        PageWrapper<Leilao> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
		model.addAttribute("status",StatusLeilao.values());
        return "leilao/mostrar_meus_leiloes :: tabela";
    }

	@GetMapping("/abrirpesquisar")
    public String abrirPesquisa(String gadoBusca, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		model.addAttribute("leilaoDTOInput", new LeilaoDTOInput());
		model.addAttribute("statuses",StatusLeilao.values());
        return "leilao/pesquisar :: formulario";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(LeilaoFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails) {
		Long usuarioCodigo = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        Page<Leilao> pagina = leilaoService.pesquisarLeiloes(filtro, pageable, usuarioCodigo);
        logger.info("Leiloes do usuario {} pesquisados: {}", userDetails.getUsername(), pagina.getContent());
        PageWrapper<Leilao> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
		model.addAttribute("status",StatusLeilao.values());
        return "leilao/mostrar :: tabela";
    }

    @GetMapping("/abrirpesquisarmeuslances")
    public String abrirPesquisaMeusLances(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<String> venceuounaoList = new ArrayList<>();
        venceuounaoList.add("Venceu");
        venceuounaoList.add("Não venceu");
        model.addAttribute("venceuounao",venceuounaoList);
        List<StatusLeilao> statuses = new ArrayList<>();
        for(StatusLeilao status: StatusLeilao.values()){
            if(status != StatusLeilao.AGUARDANDO){
                statuses.add(status);
            }
        }
		model.addAttribute("statuses",statuses);
        return "leilao/pesquisar_meus_lances :: formulario";
    }

    @GetMapping("/pesquisarmeuslances")
    public String pesquisarMeusLances(LeilaoBidHistoryFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails) {
		Long usuarioCodigo = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        Page<LeilaoBidHistory> pagina = leilaoBidHistoryService.pesquisarUsuario(filtro, pageable, usuarioCodigo);
        logger.info("Lances do usuario {} pesquisados: {}", userDetails.getUsername(), pagina.getContent());
        PageWrapper<LeilaoBidHistory> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
		model.addAttribute("status",StatusLeilao.values());
        return "leilao/mostrar_meus_lances :: tabela";
    }

	@GetMapping("/cadastrar")
	public String abrirCadastroLeilao(LeilaoDTOInput leilao, Model model) {
		return "leilao/cadastrar :: formulario";
	}

    @GetMapping("/pesquisargadocadastrar")
    public String pesquisarGado(String gadoBusca, Model model,@AuthenticationPrincipal UserDetails userDetails) {
		Long codigo_usuario = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        List<Gado> gados = gadoService.pesquisarGeralNotInLeilao(gadoBusca, codigo_usuario);
        logger.debug("Gados buscados: {}", gados);
        model.addAttribute("gados", gados);
        return "gado/listar :: lista";
    }

	@GetMapping("/pesquisargado")
    public String pesquisarGadoCadastrar(String gadoBusca, Model model,@AuthenticationPrincipal UserDetails userDetails) {
		Long codigo_usuario = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        List<Gado> gados = gadoService.pesquisarGeral(gadoBusca, codigo_usuario);
        logger.debug("Gados buscados: {}", gados);
        model.addAttribute("gados", gados);
        return "gado/listar :: lista";
    }

    @GetMapping("/pesquisargadolances")
    public String pesquisarGadoLances(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		Long codigo_usuario = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        List<LeilaoBidHistory> leilaoBidHistoryList = leilaoBidHistoryService.buscarTodosLancesUsuario(codigo_usuario);
        Set<Gado> gadosSet = new HashSet<>();
        for(LeilaoBidHistory lance: leilaoBidHistoryList){
            if(lance.getLeilao() != null && lance.getLeilao().getGado() != null){
                gadosSet.add(lance.getLeilao().getGado());
            }
        }
        List<Gado> gados = new ArrayList<>(gadosSet);
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
                return "leilao/cadastrar :: formulario";
            }
			leilao.setUsuario(usuario);
			leilaoService.salvar(leilao.toLeilao());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Cadastro de leilão efetuado com sucesso.",
                    TipoNotificaoSweetAlert2.SUCCESS, 4000));
			return "redirect:/leilao/cadastrar";
		}
	}

	@PostMapping("/alterar")
	public String alterarGado(@Valid LeilaoDTOInput leilao, BindingResult resultado, 
		RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (resultado.hasErrors()) {
			logger.info("Algum dado do leilao recebido para alterar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			return "leilao/alterar :: formulario";
		} else {
			leilaoService.atualizar(leilao.toLeilao());
			redirectAttributes.addAttribute("codigo",leilao.getCodigo());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Dados do leilao alterados com sucesso.",
			TipoNotificaoSweetAlert2.SUCCESS, 4000));
			
			return "redirect:/leilao/alterar/{codigo}";
		}
	}
    
	@GetMapping("/alterar/{codigo}")
    public String abrirAlterar(@PathVariable Long codigo, Model model) {
        LeilaoDTOInput dto = LeilaoDTOInput.fromLeilao(leilaoService.buscar(codigo));
        model.addAttribute("leilaoDTOInput", dto);
        return "leilao/alterar :: formulario";
    }

	@GetMapping("/remover/{codigo}")
    public String remover(@PathVariable Long codigo, RedirectAttributes atributos) {
        Leilao leilao = leilaoService.buscar(codigo);
        if (leilao != null) {
			leilao.setAtivo(false);
			leilaoService.atualizar(leilao);
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Leilao removido com sucesso.",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        } else {
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Não foi encontrado um leilao com esse codigo",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        }
        return "redirect:/leilao/pesquisarmeusleiloes";
    }

	
	@GetMapping("/ativar/{codigo}")
    public String ativar(@PathVariable Long codigo, RedirectAttributes atributos) {
        Leilao leilao = leilaoService.buscar(codigo);
        if (leilao != null) {
			if (leilao.getFinalTimestamp().isBefore(LocalDateTime.now())) {
				atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Leilão com data no passado.",
				TipoNotificaoSweetAlert2.ERROR, 4000));
				return "redirect:/leilao/pesquisarmeusleiloes";
			}
			leilao.setStatus(StatusLeilao.ABERTO);
			leilaoService.atualizar(leilao);
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Leilao aberto com sucesso.",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        } else {
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Não foi encontrado um leilao com esse codigo",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        }
        return "redirect:/leilao/pesquisarmeusleiloes";
    }

	@GetMapping("/lance/{codigo}")
	public String abrirModalLance(@PathVariable Long codigo, Model model) {
		Leilao leilao = leilaoService.buscar(codigo); 
        LeilaoBidHistory leilaoBidHistory = leilaoBidHistoryService.buscarUltimoLance(codigo);
        model.addAttribute("ultimoLance",leilaoBidHistory);
		model.addAttribute("leilao", leilao);
		return "leilao/modal_lance :: modal"; 
	}

	@PostMapping("/lance/salvar")
	public String salvarLanceNovo(Long leilaoCodigo, java.math.BigDecimal valorLance, 
                            @AuthenticationPrincipal UserDetails userDetails, 
                            Model model, 
                            @PageableDefault(size = 9) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable, // 🔍 CORREÇÃO 1: Mantém o padrão de 9 itens
                            HttpServletRequest request,
                            HttpServletResponse response) {
    
    Usuario usuario = usuarioRepository.findByNome(userDetails.getUsername());
    LeilaoBidHistory leilaoBidHistory = leilaoBidHistoryService.buscarUltimoLance(leilaoCodigo);
    Leilao leilao = leilaoService.buscar(leilaoCodigo);
    java.math.BigDecimal initialPrice;
    if(leilaoBidHistory == null){
        initialPrice = leilao.getInitialPrice();
    }
    else{
        initialPrice = leilaoBidHistory.getBidValue();
    }
    
    if (valorLance == null || valorLance.compareTo(initialPrice) <= 0) {
        model.addAttribute("leilao", leilao);
        model.addAttribute("ultimoLance", leilaoBidHistory);
        model.addAttribute("erroLance", "O seu lance não pode ser menor que o lance inicial de R$ " + initialPrice);
        
        response.setHeader("HX-Retarget", "#modal-container");
        response.setHeader("HX-Reswap", "innerHTML");
        
        return "leilao/modal_lance :: modal"; 
    }
    
    LeilaoBidHistory lance = new LeilaoBidHistory();
    lance.setLeilao(leilao);
    lance.setUsuario(usuario);
    lance.setBidValue(valorLance);
    lance.setTimestampDeCriacao(LocalDateTime.now());
    leilaoBidHistoryService.salvar(lance);
    
    logger.info("Usuário {} deu um lance de R$ {} no leilão {}", usuario.getNome(), valorLance, leilao.getNome());

    Page<Leilao> pagina = leilaoService.pesquisarLeiloes(new LeilaoFilter(), pageable, usuario.getCodigo());
    PageWrapper<Leilao> paginaWrapper = new PageWrapper<>(pagina, request);
    
    model.addAttribute("pagina", paginaWrapper);
    model.addAttribute("status", StatusLeilao.values());
    model.addAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Lance dado com sucesso.",
            TipoNotificaoSweetAlert2.SUCCESS, 4000));
            
    return "leilao/mostrar :: tabela";
}
	
}

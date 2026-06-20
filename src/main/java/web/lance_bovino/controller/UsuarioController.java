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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.lance_bovino.dto.UsuarioDTOInput;
import web.lance_bovino.filter.UsuarioFilter;
import web.lance_bovino.model.BankMethod;
import web.lance_bovino.model.Papel;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import web.lance_bovino.pagination.PageWrapper;
import web.lance_bovino.repository.PapelRepository;
import web.lance_bovino.repository.UsuarioRepository;
import web.lance_bovino.service.UsuarioService;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	private PapelRepository papelRepository;
	private UsuarioService usuarioService;
	private PasswordEncoder passwordEncoder;
	private UsuarioRepository usuarioRepository;
	
	public UsuarioController(PapelRepository papelRepository, UsuarioService usuarioService,
			PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
		this.papelRepository = papelRepository;
		this.usuarioService = usuarioService;
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping("/abrirpesquisar")
    public String abrirPesquisa(Model model) {
		model.addAttribute("metodosBancarios", BankMethod.values());
        return "usuario/pesquisar :: formulario";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(UsuarioFilter filtro, Model model,
            @PageableDefault(size = 9) @SortDefault(sort = "codigo",
                    direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails) {
		Long usuarioCodigo = usuarioRepository.findByNome(userDetails.getUsername()).getCodigo();
        Page<Usuario> pagina = usuarioService.pesquisar(filtro, pageable, usuarioCodigo);
        logger.info("Usuarios pesquisadas: {}", pagina.getContent());
        PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "usuario/mostrar :: tabela";
    }

	@GetMapping("/cadastrar")
	public String abrirCadastroUsuario(UsuarioDTOInput usuario, Model model) {
		model.addAttribute("metodosBancarios", BankMethod.values());
		return "usuario/cadastrar :: formulario";
	}
	
	@PostMapping("/cadastrar")
	public String cadastrarNovoUsuario(@Validated(UsuarioDTOInput.CreateLogin.class) UsuarioDTOInput usuario, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {
		if (resultado.hasErrors()) {
			logger.info("O usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			model.addAttribute("metodosBancarios", BankMethod.values());
			return "usuario/cadastrar :: formulario";
		} else {
			Papel usuario_papel = papelRepository.findByNome("ROLE_USUARIO");
			List<Papel> papeis = new ArrayList<>();
			papeis.add(usuario_papel);
			usuario.setPapeis(papeis);
			usuario.setAtivo(true);
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuarioService.salvar(usuario.toUsuario());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Cadastro de usuário efetuado com sucesso.",
                    TipoNotificaoSweetAlert2.SUCCESS, 4000));
			return "redirect:/usuario/cadastrar";
		}
	}

	@GetMapping("/cadastrar_admin")
	public String abrirCadastroUsuarioAdmin(UsuarioDTOInput usuario, Model model) {
		List<Papel> papeis = papelRepository.findAll();
		model.addAttribute("todosPapeis", papeis);
		model.addAttribute("metodosBancarios", BankMethod.values());
		return "usuario/cadastrar_admin :: formulario";
	}
	
	@PostMapping("/cadastrar_admin")
	public String cadastrarNovoUsuarioAdmin(@Validated(UsuarioDTOInput.AdminCreateUser.class) UsuarioDTOInput usuario, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {
		if (resultado.hasErrors()) {
			logger.info("O usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			List<Papel> papeis = papelRepository.findAll();
			model.addAttribute("todosPapeis", papeis);
			model.addAttribute("metodosBancarios", BankMethod.values());
			return "usuario/cadastrar_admin :: formulario";
		} else {
			usuario.setAtivo(true);
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuarioService.salvar(usuario.toUsuario());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Cadastro de usuário efetuado com sucesso.",
                    TipoNotificaoSweetAlert2.SUCCESS, 4000));
			return "redirect:/usuario/cadastrar_admin";
		}
	}

	@GetMapping("/alterar")
	public String abrirAlterarUsuario(@AuthenticationPrincipal UserDetails userDetails, Model model){
		String nome_usuario = userDetails.getUsername();

		Usuario usuario = usuarioRepository.findByNome(nome_usuario);
		logger.info("usuario para alterar: {}", usuario);

		UsuarioDTOInput usuarioDTOInput = UsuarioDTOInput.fromUsuario(usuario);
		logger.info("usuariodto para alterar: {}", usuarioDTOInput.getCodigo());

		model.addAttribute("usuarioDTOInput", usuarioDTOInput);
		model.addAttribute("metodosBancarios", BankMethod.values());
		return "usuario/alterar :: formulario";
	}

	@PostMapping("/alterar")
	public String alterarUsuario(@Validated(UsuarioDTOInput.Edit.class) UsuarioDTOInput usuario, BindingResult resultado, Model model, 
		RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (resultado.hasErrors()) {
			logger.info("Algum dado do usuario recebido para alterar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			model.addAttribute("metodosBancarios", BankMethod.values());
			return "usuario/alterar :: formulario";
		} else {
			Papel usuario_papel = papelRepository.findByNome("ROLE_USUARIO");
			List<Papel> papeis = new ArrayList<>();
			papeis.add(usuario_papel);
			usuario.setPapeis(papeis);
			usuario.setAtivo(true);
			if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			}
			usuarioService.atualizar(usuario.toUsuario());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User principalAtual = (User) auth.getPrincipal();
			User novoPrincipal = new User(
				usuario.getNome(),             
				principalAtual.getPassword(), 
				principalAtual.getAuthorities()
			);
			SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(novoPrincipal, principalAtual.getPassword(), principalAtual.getAuthorities())
			);

			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Dados do usuário alterados com sucesso.",
			TipoNotificaoSweetAlert2.SUCCESS, 4000));
			
			return "redirect:/usuario/alterar";
		}
	}

	@PostMapping("/alterar_admin")
	public String alterarAdminUsuario(@Validated(UsuarioDTOInput.AdminEditUser.class) UsuarioDTOInput usuario, BindingResult resultado, Model model, 
		RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (resultado.hasErrors()) {
			logger.info("Algum dado do usuario recebido para alterar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			model.addAttribute("metodosBancarios", BankMethod.values());
			return "usuario/alterar_admin :: formulario";
		} else {
			usuario.setAtivo(true);
			if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			}
			usuarioService.atualizar(usuario.toUsuario());
			redirectAttributes.addAttribute("codigo", usuario.getCodigo());
			redirectAttributes.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Dados do usuário alterados com sucesso.",
			TipoNotificaoSweetAlert2.SUCCESS, 4000));
			
			return "redirect:/usuario/alterar/{codigo}";
		}
	}

	@GetMapping("/alterar/{codigo}")
    public String abrirAlterar(@PathVariable Long codigo, Model model) {
        UsuarioDTOInput dto = UsuarioDTOInput.fromUsuario(usuarioService.buscar(codigo));
        model.addAttribute("usuarioDTOInput", dto);
		List<Papel> papeis = papelRepository.findAll();
		model.addAttribute("todosPapeis", papeis);
		model.addAttribute("metodosBancarios", BankMethod.values());
        return "usuario/alterar_admin :: formulario";
    }

	@GetMapping("/remover/{codigo}")
    public String remover(@PathVariable Long codigo, RedirectAttributes atributos) {
        Usuario usuario = usuarioService.buscar(codigo);
        if (usuario != null) {
			usuarioService.desativar(usuario);
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Usuário removido com sucesso.",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        } else {
			atributos.addFlashAttribute("notificacaoSA2", new NotificacaoSweetAlert2("Não foi encontrado um usuario com esse codigo",
				TipoNotificaoSweetAlert2.SUCCESS, 4000));
        }
        return "redirect:/usuario/pesquisar";
    }
	
}

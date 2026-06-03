package web.lance_bovino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

@Controller
public class IndexController {

	@GetMapping(value = { "/", "/index.html" })
	public String index() {
		return "index";
	}

	@HxRequest
	@GetMapping(value = { "/", "/index.html" })
	public String indexHTMX() {
		return "index :: conteudo";
	}

	@HxRequest
	@GetMapping("/login")
	public String login() {
		return "login :: formulario";
	}

	@PostMapping("/login-error")
	public String loginError(String username, Model model) {
		model.addAttribute("username", username);
		model.addAttribute("notificacao", new NotificacaoSweetAlert2(
				"Usuário e/ou senha inválidos.", TipoNotificaoSweetAlert2.ERROR));
		return "login :: formulario";
	}

	@GetMapping("/layout/header-atualizado")
	public String obterHeaderAtualizado() {
		// Retorna o fragmento específico que contém os botões de login/logout
		return "layout/fragments/header :: usuariologinlogout";
	}

}
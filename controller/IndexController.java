package web.lance_bovino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.lance_bovino.notification.NotificacaoSweetAlert2;
import web.lance_bovino.notification.TipoNotificaoSweetAlert2;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

@Controller
public class IndexController {

    // Carregamento normal da página inicial
    @GetMapping(value = { "/", "/index.html" })
    public String index() {
        return "index";
    }

    // Carregamento via HTMX da página inicial
    @HxRequest
    @GetMapping(value = { "/", "/index.html" })
    public String indexHTMX() {
        return "index :: conteudo";
    }

    // Carregamento da página de Login (Normal ou com Erro)
    @GetMapping("/login")
    public String loginNormal(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("notificacao", new NotificacaoSweetAlert2(
                    "E-mail e/ou senha inválidos.", TipoNotificaoSweetAlert2.ERROR));
        }
        return "login";
    }

    // Carregamento do formulário de Login via HTMX (Normal ou com Erro)
    @HxRequest
    @GetMapping("/login")
    public String loginHTMX(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("notificacao", new NotificacaoSweetAlert2(
                    "E-mail e/ou senha inválidos.", TipoNotificaoSweetAlert2.ERROR));
        }
        return "login :: formulario";
    }

    // Atualiza a barra de navegação após login/logout
    @GetMapping("/layout/header-atualizado")
    public String obterHeaderAtualizado() {
        return "layout/fragments/header :: usuariologinlogout";
    }

}
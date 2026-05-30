package web.lance_bovino.controller; // Ajuste para o pacote correto do seu projeto

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import com.example.model.Leilao;
import com.example.model.StatusLeilao;

@Controller
public class LeilaoController {

    // 1. PESQUISAR
    @GetMapping("/leilao/pesquisar")
    public String abrirPesquisar() {
        // Retorna apenas o fragmento "formulario" de dentro da pasta templates/leilao/pesquisar.html
        return "leilao/pesquisar :: formulario";
    }

    @PostMapping("/leilao/pesquisar")
    public String pesquisar(Leilao leilao) {
        // Aqui no futuro você fará a busca usando o seu Repository
        return "leilao/pesquisar :: formulario";
    }

    // 2. CADASTRAR
    @GetMapping("/leilao/cadastrar")
    public String abrirCadastrar(Leilao leilao) {
        return "leilao/cadastrar :: formulario";
    }

    @PostMapping("/leilao/cadastrar")
    public String cadastrar(@Valid Leilao leilao, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "leilao/cadastrar :: formulario";
        } else {
            // No futuro, aqui você usará: leilaoRepository.save(leilao);
            return "redirect:/leilao/cadastrar";
        }
    }

    // 3. ALTERAR (Simulando dados mockados para teste, igual à sua Grosbilda)
    @GetMapping("/leilao/alterar")
    public String abrirAlterar(Leilao leilao) {
        // Preenchendo dados fictícios para testar a tela de alteração
        leilao.setId(100L);
        leilao.setNome("Grande Leilão Nelore Poções 2026");
        leilao.setInitialPrice(new BigDecimal("25000.00"));
        leilao.setFinalTimestamp(LocalDateTime.now().plusDays(2)); // Encerra daqui a 2 dias
        leilao.setStatus(StatusLeilao.ABERTO); // Usando o seu enum integrado
        
        return "leilao/alterar :: formulario";
    }

    @PostMapping("/leilao/alterar")
    public String alterar(@Valid Leilao leilao, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "leilao/alterar :: formulario";
        } else {
            // No futuro: leilaoRepository.save(leilao);
            return "redirect:/leilao/alterar";
        }
    }
}
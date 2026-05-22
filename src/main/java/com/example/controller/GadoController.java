package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class GadoController {

    private static final Logger logger = LoggerFactory.getLogger(GadoController.class);

    // private PessoaService pessoaService;

    // public PessoaController(PessoaService pessoaService) {
    //     this.pessoaService = pessoaService;
    // }

    // @GetMapping("/pessoa/abrirpesquisar")
    // public String abrirPesquisa() {
    //     return "pessoas/pesquisar :: formulario";
    // }

    // @GetMapping("/pessoa/pesquisar")
    // public String pesquisar(PessoaFilter filtro, Model model,
    //         @PageableDefault(size = 9) @SortDefault(sort = "codigo",
    //                 direction = Sort.Direction.ASC) Pageable pageable,
    //         HttpServletRequest request) {
    //     Page<Pessoa> pagina = pessoaService.pesquisar(filtro, pageable);
    //     logger.info("Pessoas pesquisadas: {}", pagina.getContent());
    //     PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
    //     model.addAttribute("pagina", paginaWrapper);
    //     return "pessoas/mostrar :: tabela";
    // }

    // @GetMapping("/pessoa/cadastrar")
    // public String abrirCadastro(PessoaDTOInput dto) {
    //     return "pessoas/cadastrar :: formulario";
    // }

    // @PostMapping("/pessoa/cadastrar")
    // public String cadastrar(@Valid PessoaDTOInput dto, BindingResult resultado,
    //         RedirectAttributes atributos) {
    //     if (resultado.hasErrors()) {
    //         logger.info("A pessoa recebida para cadastrar não é válida.");
    //         logger.info("Erros encontrados:");
    //         for (FieldError erro : resultado.getFieldErrors()) {
    //             logger.info("{}", erro);
    //         }
    //         for (ObjectError erro : resultado.getGlobalErrors()) {
    //             logger.info("{}", erro);
    //         }
    //         return "pessoas/cadastrar :: formulario";
    //     } else {
    //         pessoaService.salvar(dto.toPessoa());
    //         atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
    //                 "Pessoa cadastrada com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
    //         return "redirect:/pessoa/cadastrar";
    //     }
    // }

    // @GetMapping("/pessoa/alterar/{codigo}")
    // public String abrirAlterar(@PathVariable Long codigo, Model model) {
    //     PessoaDTOInput dto = PessoaDTOInput.fromPessoa(pessoaService.buscar(codigo));
    //     model.addAttribute("pessoaDTOInput", dto);
    //     return "pessoas/alterar :: formulario";
    // }

    // @PostMapping("/pessoa/alterar")
    // public String alterar(@Valid PessoaDTOInput dto, BindingResult resultado,
    //         RedirectAttributes atributos) {
    //     if (resultado.hasErrors()) {
    //         logger.info("A pessoa recebida para alterar não é válida.");
    //         logger.info("Erros encontrados:");
    //         for (FieldError erro : resultado.getFieldErrors()) {
    //             logger.info("{}", erro);
    //         }
    //         for (ObjectError erro : resultado.getGlobalErrors()) {
    //             logger.info("{}", erro);
    //         }
    //         return "pessoas/alterar :: formulario";
    //     } else {
    //         pessoaService.atualizar(dto.toPessoa());
    //         atributos.addFlashAttribute("notificacao", new NotificacaoSweetAlert2("Pessoa alterada com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
    //         return "redirect:/pessoa/abrirpesquisar";
    //     }
    // }

    // @GetMapping("/pessoa/remover/{codigo}")
    // public String remover(@PathVariable Long codigo, RedirectAttributes atributos) {
    //     Pessoa pessoa = pessoaService.buscar(codigo);
    //     if (pessoa != null) {
    //         pessoa.setStatus(Status.INATIVO);
    //         pessoaService.atualizar(pessoa);
    //         atributos.addFlashAttribute("mensagem", "Pessoa removida com sucesso");
    //     } else {
    //         atributos.addFlashAttribute("mensagem",
    //                 "Não foi encontrada uma pessoa com esse codigo");
    //     }
    //     return "redirect:/mensagem";
    // }
}

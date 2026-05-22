package web.controlevacinacao.service; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.controlevacinacao.filter.PessoaFilter;
import web.controlevacinacao.model.Pessoa;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.repository.PessoaRepository;

@Service 
public class UsuarioService { 

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class); 

    private UsuarioRepository pessoaRepository;

    public UsuarioService(UsuarioRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public Page<Pessoa> pesquisar(GadoFilter filtro, Pageable pageable) {
        logger.info("Pesquisando pessoas com o filtro {}", filtro);
        return pessoaRepository.pesquisar(filtro, pageable);
    }

    @Transactional(readOnly = true)
    public Pessoa buscarPeloCPF(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }

    @Transactional 
    public void salvar(Pessoa pessoa) { 
        logger.info("Salvando pessoa: {}", pessoa); 
        pessoaRepository.save(pessoa); 
    } 

    @Transactional 
    public void atualizar(Pessoa pessoa) { 
        logger.info("Atualizando pessoa: {}", pessoa); 
        pessoaRepository.save(pessoa); 
    } 

    @Transactional 
    public void remover(Long codigo) { 
        logger.info("Removendo pessoa com código: {}", codigo); 
        pessoaRepository.deleteById(codigo); 
    } 

    @Transactional(readOnly = true)
    public Pessoa buscar(Long codigo) {
        logger.info("Buscando a pessoa com código: {}", codigo);
        return pessoaRepository.findByCodigoAndStatus(codigo, Status.ATIVO).orElse(null);
    }
} 
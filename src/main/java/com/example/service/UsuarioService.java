package com.example.service; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.filter.UsuarioFilter;
import com.example.model.User;
import com.example.model.Status;
import com.example.repository.UsuarioRepository;

@Service 
public class UsuarioService { 

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class); 

    private UsuarioRepository pessoaRepository;

    public UsuarioService(UsuarioRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public Page<User> pesquisar(UsuarioFilter filtro, Pageable pageable) {
        logger.info("Pesquisando usuários com o filtro {}", filtro);
        return pessoaRepository.pesquisar(filtro, pageable);
    }

    @Transactional(readOnly = true)
    public User buscarPeloCPF(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }

    @Transactional 
    public void salvar(User pessoa) { 
        logger.info("Salvando pessoa: {}", pessoa); 
        pessoaRepository.save(pessoa); 
    } 

    @Transactional 
    public void atualizar(User pessoa) { 
        logger.info("Atualizando pessoa: {}", pessoa); 
        pessoaRepository.save(pessoa); 
    } 

    @Transactional 
    public void remover(Long codigo) { 
        logger.info("Removendo pessoa com código: {}", codigo); 
        pessoaRepository.deleteById(codigo); 
    } 

    @Transactional(readOnly = true)
    public User buscar(Long codigo) {
        logger.info("Buscando o usuário com código: {}", codigo);
        return pessoaRepository.findByCodigoAndStatus(codigo, Status.ATIVO).orElse(null);
    }
} 
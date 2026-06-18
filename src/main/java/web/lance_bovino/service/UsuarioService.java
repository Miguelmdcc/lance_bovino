package web.lance_bovino.service; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.model.Usuario;
import web.lance_bovino.repository.UsuarioRepository;

@Service 
public class UsuarioService { 

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class); 

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // @Transactional(readOnly = true)
    // public Page<Usuario> pesquisar(UsuarioFilter filtro, Pageable pageable) {
    //     logger.info("Pesquisando usuários com o filtro {}", filtro);
    //     return usuarioRepository.pesquisar(filtro, pageable);
    // }

    @Transactional(readOnly = true)
    public Usuario buscarPeloCPF(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    @Transactional 
    public void salvar(Usuario usuario) { 
        logger.info("Salvando usuario: {}", usuario); 
        usuarioRepository.save(usuario); 
    } 

    @Transactional 
    public void atualizar(Usuario usuario) { 
        logger.info("Atualizando usuario: {}", usuario); 
        usuarioRepository.save(usuario); 
    } 

    @Transactional 
    public void remover(Long codigo) { 
        logger.info("Removendo usuario com código: {}", codigo); 
        usuarioRepository.deleteById(codigo); 
    } 

    @Transactional(readOnly = true)
    public Usuario buscar(Long codigo) {
        logger.info("Buscando o usuário com código: {}", codigo);
        return usuarioRepository.findByCodigoAndAtivo(codigo, true).orElse(null);
    }
} 
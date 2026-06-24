package web.lance_bovino.service; 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.filter.GadoFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Status;
import web.lance_bovino.repository.GadoRepository;

@Service 
public class GadoService { 

    private static final Logger logger = LoggerFactory.getLogger(GadoService.class); 

    private GadoRepository gadoRepository;

    public GadoService(GadoRepository gadoRepository) {
        this.gadoRepository = gadoRepository;
    }

    @Transactional(readOnly = true)
    public Page<Gado> pesquisar(GadoFilter filtro, Pageable pageable, Long usuarioCodigo) {
        logger.info("Pesquisando gados com o filtro {}", filtro);
        return gadoRepository.pesquisar(filtro, pageable, usuarioCodigo);
    }

    @Transactional 
    public void salvar(Gado gado) { 
        logger.info("Salvando gado: {}", gado); 
        gadoRepository.save(gado); 
    } 

    @Transactional 
    public void atualizar(Gado gado) { 
        logger.info("Atualizando gado: {}", gado); 
        gadoRepository.save(gado); 
    } 

    @Transactional 
    public void remover(Long codigo) { 
        logger.info("Removendo gado com código: {}", codigo); 
        gadoRepository.deleteById(codigo); 
    } 

    @Transactional(readOnly = true)
    public Gado buscar(Long codigo) {
        logger.info("Buscando o gado com código: {}", codigo);
        return gadoRepository.findByCodigoAndStatus(codigo, Status.ATIVO).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Gado> pesquisarGeral(String busca, Long codigo_usuario) {
        return gadoRepository.pesquisarGeral(busca, codigo_usuario);
    }

    @Transactional(readOnly = true)
    public List<Gado> pesquisarGeralNotInLeilao(String busca, Long codigo_usuario) {
        return gadoRepository.pesquisarGeralNotInLeilao(busca, codigo_usuario);
    }

    @Transactional(readOnly = true)
    public Page<Gado> pesquisarTodos(GadoFilter filtro, Pageable pageable) {
        logger.info("Pesquisando gados com o filtro {}", filtro);
        return gadoRepository.pesquisarTodos(filtro, pageable);
    }
} 
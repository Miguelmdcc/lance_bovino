package web.lance_bovino.service; 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.filter.LeilaoFilter;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.Status;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.repository.LeilaoRepository;

@Service 
public class LeilaoService { 

    private static final Logger logger = LoggerFactory.getLogger(LeilaoService.class); 

    private LeilaoRepository leilaoRepository;

    public LeilaoService(LeilaoRepository leilaoRepository) {
        this.leilaoRepository = leilaoRepository;
    }

    @Transactional
    public Page<Leilao> pesquisarUsuario(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo) {
        logger.info("Pesquisando leilões com o filtro {}", filtro);
        leilaoRepository.atualizarStatusLeiloes();
        return leilaoRepository.pesquisar(filtro, pageable, usuarioCodigo);
    }

    @Transactional
    public Page<Leilao> pesquisarLeiloes(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo) {
        logger.info("Pesquisando leilões com o filtro {}", filtro);
        leilaoRepository.atualizarStatusLeiloes();
        return leilaoRepository.pesquisarLeiloes(filtro, pageable, usuarioCodigo);
    }

    @Transactional
    public void atualizarStatusLeiloes(){
        logger.info("Atualizando status dos leiloes");
        leilaoRepository.atualizarStatusLeiloes();
    }

    @Transactional 
    public void salvar(Leilao leilao) { 
        logger.info("Salvando leilao: {}", leilao); 
        leilaoRepository.save(leilao); 
    } 

    @Transactional 
    public void atualizar(Leilao leilao) { 
        logger.info("Atualizando leilao: {}", leilao); 
        leilaoRepository.save(leilao); 
    } 

    @Transactional(readOnly = true)
    public Leilao buscar(Long codigo) {
        logger.info("Buscando o leilao com código: {}", codigo);
        return leilaoRepository.findByCodigoAndStatus(codigo, StatusLeilao.AGUARDANDO);
    }

    @Transactional(readOnly = true)
    public List<Leilao> pesquisarGeral(String busca) {
        return leilaoRepository.pesquisarGeral(busca);
    }
} 
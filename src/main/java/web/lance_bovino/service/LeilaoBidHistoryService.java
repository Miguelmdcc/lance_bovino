package web.lance_bovino.service; 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.model.LeilaoBidHistory;
import web.lance_bovino.repository.LeilaoBidHistoryRepository;

@Service 
public class LeilaoBidHistoryService { 

    private static final Logger logger = LoggerFactory.getLogger(LeilaoBidHistoryService.class); 

    private LeilaoBidHistoryRepository leilaoBIdHRepository;

    public LeilaoBidHistoryService(LeilaoBidHistoryRepository leilaoBIdHRepository) {
        this.leilaoBIdHRepository = leilaoBIdHRepository;
    }

    @Transactional 
    public void salvar(LeilaoBidHistory leilaoBIdH) { 
        logger.info("Salvando leilaoBIdH: {}", leilaoBIdH); 
        leilaoBIdHRepository.save(leilaoBIdH); 
    } 

    @Transactional 
    public void atualizar(LeilaoBidHistory leilaoBIdH) { 
        logger.info("Atualizando leilaoBIdH: {}", leilaoBIdH); 
        leilaoBIdHRepository.save(leilaoBIdH); 
    } 

    @Transactional 
    public void remover(Long codigo) { 
        logger.info("Removendo leilaoBIdH com código: {}", codigo); 
        leilaoBIdHRepository.deleteById(codigo); 
    }

    @Transactional(readOnly = true)
    public LeilaoBidHistory buscarUltimoLance(Long codigo) { 
        logger.info("Removendo leilaoBIdH com código: {}", codigo); 
        return leilaoBIdHRepository.buscarUltimoLance(codigo); 
    }

} 
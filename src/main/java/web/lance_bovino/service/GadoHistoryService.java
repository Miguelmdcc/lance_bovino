package web.lance_bovino.service; 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.model.GadoHistory;
import web.lance_bovino.repository.GadoHistoryRepository;

@Service 
public class GadoHistoryService { 

    private static final Logger logger = LoggerFactory.getLogger(GadoHistoryService.class); 

    private GadoHistoryRepository gadohistoryRepository;

    public GadoHistoryService(GadoHistoryRepository gadohistoryRepository) {
        this.gadohistoryRepository = gadohistoryRepository;
    }

    @Transactional 
    public void salvar(GadoHistory gadohistory) { 
        logger.info("Salvando gadohistory: {}", gadohistory); 
        gadohistoryRepository.save(gadohistory); 
    } 

    @Transactional 
    public void atualizar(GadoHistory gadohistory) { 
        logger.info("Atualizando gadohistory: {}", gadohistory); 
        gadohistoryRepository.save(gadohistory); 
    } 

    @Transactional 
    public void remover(Long codigo) { 
        logger.info("Removendo gadohistory com código: {}", codigo); 
        gadohistoryRepository.deleteById(codigo); 
    }

} 
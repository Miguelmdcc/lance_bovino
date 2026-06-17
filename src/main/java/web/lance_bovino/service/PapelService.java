package web.lance_bovino.service; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.model.Papel;
import web.lance_bovino.repository.PapelRepository;

@Service 
public class PapelService { 

    private static final Logger logger = LoggerFactory.getLogger(PapelService.class); 

    private PapelRepository papelRepository;

    public PapelService(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    @Transactional(readOnly = true)
    public Papel buscarPapel(String nome) {
        return papelRepository.findByNome(nome);
    }
} 
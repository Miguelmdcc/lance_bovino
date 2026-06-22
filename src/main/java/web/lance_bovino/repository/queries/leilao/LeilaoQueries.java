package web.lance_bovino.repository.queries.leilao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.lance_bovino.model.Leilao;

public interface LeilaoQueries {

	Page<Leilao> pesquisar(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo);

	List<Leilao> pesquisarGeral(String filtro);
	
}

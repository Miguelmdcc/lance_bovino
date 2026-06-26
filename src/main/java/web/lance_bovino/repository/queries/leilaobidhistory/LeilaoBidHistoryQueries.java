package web.lance_bovino.repository.queries.leilaobidhistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.lance_bovino.filter.LeilaoFilter;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.LeilaoBidHistory;

public interface LeilaoBidHistoryQueries {

	Page<Leilao> pesquisar(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo);

	LeilaoBidHistory buscarUltimoLance(Long codigo);
	
}

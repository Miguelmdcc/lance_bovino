package web.lance_bovino.repository.queries.leilaobidhistory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.lance_bovino.filter.LeilaoBidHistoryFilter;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.LeilaoBidHistory;

public interface LeilaoBidHistoryQueries {

	Page<LeilaoBidHistory> pesquisar(LeilaoBidHistoryFilter filtro, Pageable pageable, Long usuarioCodigo);

	LeilaoBidHistory buscarUltimoLance(Long codigo);

	void atualizarVencedores(List<Leilao> leiloesEncerrados);
	
}

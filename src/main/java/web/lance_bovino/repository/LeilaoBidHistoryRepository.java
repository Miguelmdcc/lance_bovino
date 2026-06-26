package web.lance_bovino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.LeilaoBidHistory;
import web.lance_bovino.repository.queries.leilaobidhistory.LeilaoBidHistoryQueries;

public interface LeilaoBidHistoryRepository extends JpaRepository<LeilaoBidHistory, Long>, LeilaoBidHistoryQueries {

    LeilaoBidHistory buscarUltimoLance(Long codigo);

    List<LeilaoBidHistory> findAllByUsuarioCodigo(Long Codigo);

    void atualizarVencedores(List<Leilao> leiloesEncerrados);

}

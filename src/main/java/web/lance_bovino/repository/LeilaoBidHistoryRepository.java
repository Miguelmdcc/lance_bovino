package web.lance_bovino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.lance_bovino.model.LeilaoBidHistory;
import web.lance_bovino.repository.queries.leilaobidhistory.LeilaoBidHistoryQueries;

public interface LeilaoBidHistoryRepository extends JpaRepository<LeilaoBidHistory, Long>, LeilaoBidHistoryQueries {

    LeilaoBidHistory buscarUltimoLance(Long codigo);

}

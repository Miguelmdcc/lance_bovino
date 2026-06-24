package web.lance_bovino.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import web.lance_bovino.filter.LeilaoFilter;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.repository.queries.leilao.LeilaoQueries;

public interface LeilaoRepository extends JpaRepository<Leilao, Long>, LeilaoQueries {

    Leilao findByCodigoAndStatus(Long codigo, StatusLeilao statusLeilao);

    void atualizarStatusLeiloes();

    Page<Leilao> pesquisarLeiloes(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo);

}

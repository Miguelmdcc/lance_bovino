package web.lance_bovino.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import web.lance_bovino.filter.GadoFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Status;
import web.lance_bovino.repository.queries.gado.GadoQueries;

public interface GadoRepository extends JpaRepository<Gado, Long>, GadoQueries {

    Optional<Gado> findByCodigoAndStatus(long codigo, Status status);

    List<Gado> pesquisarGeralNotInLeilao(String busca, Long codigo_usuario);

    Page<Gado> pesquisarTodos(GadoFilter filtro, Pageable pageable);

}

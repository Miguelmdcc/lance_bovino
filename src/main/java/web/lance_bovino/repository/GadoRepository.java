package web.lance_bovino.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Status;
import web.lance_bovino.repository.queries.gado.GadoQueries;

public interface GadoRepository extends JpaRepository<Gado, Long>, GadoQueries {

    Optional<Gado> findByCodigoAndStatus(long codigo, Status status);

}

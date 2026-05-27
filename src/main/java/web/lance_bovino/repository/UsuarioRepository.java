package web.lance_bovino.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import web.lance_bovino.model.Status;
import web.lance_bovino.model.User;
import web.lance_bovino.repository.queries.usuario.UsuarioQueries;

public interface UsuarioRepository extends JpaRepository<User, Long>, UsuarioQueries {

    Optional<User> findByCodigoAndStatus(long codigo, Status status);

    User findByCpf(String cpf);

}

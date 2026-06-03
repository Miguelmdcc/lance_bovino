package web.lance_bovino.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import web.lance_bovino.model.Status;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.repository.queries.usuario.UsuarioQueries;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQueries {

    Optional<Usuario> findByCodigoAndStatus(long codigo, Status status);

    Usuario findByCpf(String cpf);

}

package web.lance_bovino.repository.queries.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.lance_bovino.filter.UsuarioFilter;
import web.lance_bovino.model.User;

public interface UsuarioQueries {

	Page<User> pesquisar(UsuarioFilter filtro, Pageable pageable);
	
}

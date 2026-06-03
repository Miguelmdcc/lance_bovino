package web.lance_bovino.repository.queries.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.lance_bovino.filter.UsuarioFilter;
import web.lance_bovino.model.Usuario;

public interface UsuarioQueries {

	Page<Usuario> pesquisar(UsuarioFilter filtro, Pageable pageable);
	
}

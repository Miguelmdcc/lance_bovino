package web.lance_bovino.repository.queries.gado;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.lance_bovino.filter.GadoFilter;
import web.lance_bovino.model.Gado;

public interface GadoQueries {

	Page<Gado> pesquisar(GadoFilter filtro, Pageable pageable, Long usuarioCodigo);

	List<Gado> pesquisarGeral(String filtro, Long codigo_usuario);

	List<Gado> pesquisarGeralNotInLeilao(String filtro, Long codigo_usuario);
	
}

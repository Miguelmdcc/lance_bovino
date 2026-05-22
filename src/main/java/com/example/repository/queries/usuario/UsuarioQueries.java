package com.example.repository.queries.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.controlevacinacao.filter.PessoaFilter;
import web.controlevacinacao.model.Pessoa;

public interface UsuarioQueries {

	Page<Pessoa> pesquisar(GadoFilter filtro, Pageable pageable);
	
}

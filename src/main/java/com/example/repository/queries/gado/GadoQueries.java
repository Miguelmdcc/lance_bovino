package com.example.repository.queries.gado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.filter.GadoFilter;
import com.example.model.Gado;

public interface GadoQueries {

	Page<Gado> pesquisar(GadoFilter filtro, Pageable pageable);
	
}

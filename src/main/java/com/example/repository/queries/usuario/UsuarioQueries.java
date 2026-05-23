package com.example.repository.queries.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.filter.UsuarioFilter;
import com.example.model.User;

public interface UsuarioQueries {

	Page<User> pesquisar(UsuarioFilter filtro, Pageable pageable);
	
}

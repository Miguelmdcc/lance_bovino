package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.User;
import com.example.model.Status;
import com.example.repository.queries.usuario.UsuarioQueries;

public interface UsuarioRepository extends JpaRepository<User, Long>, UsuarioQueries {

    Optional<User> findByCodigoAndStatus(long codigo, Status status);

    User findByCpf(String cpf);

}

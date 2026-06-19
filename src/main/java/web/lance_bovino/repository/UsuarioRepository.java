package web.lance_bovino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.lance_bovino.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByNomeIgnoreCase(String nomeUsuario);

	Usuario findByNome(String nomeUsuario);

	Optional<Usuario> findByCodigoAndAtivo(long codigo, boolean ativo);

    Usuario findByCpf(String cpf);
	
}

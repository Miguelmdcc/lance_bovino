package web.lance_bovino.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.lance_bovino.model.Usuario;
import web.lance_bovino.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public void salvar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
}

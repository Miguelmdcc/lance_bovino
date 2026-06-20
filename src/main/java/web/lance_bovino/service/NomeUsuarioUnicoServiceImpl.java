package web.lance_bovino.service;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.lance_bovino.dto.UsuarioDTOInput;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.repository.UsuarioRepository;

@Service
public class NomeUsuarioUnicoServiceImpl implements NomeUsuarioUnicoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
		if (!fieldName.equals("nome")) {
			throw new UnsupportedOperationException("A anotação deveria ser usada no atributo nome");
		}

		UsuarioDTOInput novo = (UsuarioDTOInput) value;
		//A validacao se foi preenchido um nome nao eh obrigacao dessa verificacao
		if (novo.getNome() == null || novo.getNome().isBlank()) {
			return true;
		}
		
		//Busca um  com esse nome
		Usuario comEsseNomeUsuario = usuarioRepository.findByNomeIgnoreCase(novo.getNome());
		
		//Nao existe um usuario com esse nome, entao tudo bem
		if (comEsseNomeUsuario == null) {
			return true;
		} else {  //Existe um contato com esse nome
			//Estao tentando validar um novo usuario com um nome que ja existe 
			if (novo.getCodigo() == null) {
				return false;
			} else {  //O usuario sendo validado ja existe
				Usuario antigo = usuarioRepository.findById(novo.getCodigo()).orElseThrow(() -> new InvalidParameterException("O código do usuario a validar não existe."));
				// Se o usuario sendo validado for o mesmo que ja existia no BD entao tudo bem
				if (comEsseNomeUsuario.equals(antigo)) {
					return true;
				}
				// Senao eh pq estao tentando validar um nome que eh de outro usuario
				return false;
			}
		}
	}
}

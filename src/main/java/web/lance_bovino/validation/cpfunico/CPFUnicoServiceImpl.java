package web.lance_bovino.validation.cpfunico;

import java.security.InvalidParameterException;

import org.springframework.stereotype.Service;
import web.lance_bovino.dto.UsuarioDTOInput;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.service.UsuarioService;

@Service
public class CPFUnicoServiceImpl implements CPFUnicoService {	
	
	private UsuarioService usuarioService;

    public CPFUnicoServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

	@Override
	public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
		if (!fieldName.equals("cpf")) {
			throw new UnsupportedOperationException("A anotação deveria ser usada no atributo cpf");
		}

		Usuario novo = ((UsuarioDTOInput) value).toUser();
		//A validacao "foi preenchido um cpf" nao eh obrigacao dessa verificacao
		if (novo.getCpf() == null || novo.getCpf().isBlank()) {
			return true;
		}
		
		//Busca um usuario com esse CPF
		Usuario comEsseCPF = usuarioService.buscarPeloCPF(novo.getCpf());
		
		//Nao existe um usuario com esse cpf, entao tudo bem
		if (comEsseCPF == null) {
			return true;
		} else {  //Existe um usuario com esse cpf
			//Estao tentando validar um novo usuario com um cpf que ja existe 
			if (novo.getCodigo() == null) {
				return false;
			} else {  //O usuario sendo validado ja existe
				Usuario antigo = usuarioService.buscar(novo.getCodigo());
                if (antigo == null) {
                    throw new InvalidParameterException("O código do contato a validar não existe.");
                }
				// Se o cpf sendo validado for o mesmo que ja existia no BD entao tudo bem
				if (comEsseCPF.equals(antigo)) {
					return true;
				}
				// Senao eh pq estao tentando validar um cpf que eh de outra pessoa
				return false;
			}
		}
	}
}

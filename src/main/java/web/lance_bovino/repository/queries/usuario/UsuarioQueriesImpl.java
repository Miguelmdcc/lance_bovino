package web.lance_bovino.repository.queries.usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import web.lance_bovino.filter.UsuarioFilter;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.pagination.PaginacaoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class UsuarioQueriesImpl implements UsuarioQueries {

	@PersistenceContext
	private EntityManager em;

   	public Page<Usuario> pesquisar(UsuarioFilter filtro, Pageable pageable) {

		StringBuilder queryUsuarios = new StringBuilder("select distinct u from User u");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();

		preencherCondicoesEParametros(filtro, condicoes, parametros);

		if (condicoes.isEmpty()) {
			condicoes.append(" where u.ativo = true");
		} else {
			condicoes.append(" and u.ativo = true");
		}

		queryUsuarios.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryUsuarios, "u", pageable);
		TypedQuery<Usuario> typedQuery = em.createQuery(queryUsuarios.toString(), Usuario.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Usuario> usuarios = typedQuery.getResultList();

		long totalUsuarios = PaginacaoUtil.getTotalRegistros("User", "u", condicoes, parametros, em);

		return new PageImpl<>(usuarios, pageable, totalUsuarios);
	}

	private void preencherCondicoesEParametros(UsuarioFilter filtro, StringBuilder condicoes, Map<String, Object> parametros) {
		boolean condicao = false;

		if (filtro.getCodigo() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("u.codigo = :codigo");
			parametros.put("codigo", filtro.getCodigo());
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getNome())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);		
			condicoes.append("lower(u.nome) like :nome");
			parametros.put("nome", "%" + filtro.getNome().toLowerCase() + "%");
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getCpf())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("u.cpf like :cpf");
			parametros.put("cpf", "%" + filtro.getCpf().toLowerCase() + "%");
			condicao = true;
		}

		if (filtro.getMetodo_bancario() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("u.metodo_bancario = :metodo_bancario");
			parametros.put("metodo_bancario", filtro.getMetodo_bancario());
			condicao = true;
		}

		if (filtro.getDadosBancarios() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("u.dadosBancarios = :dadosBancarios");
			parametros.put("dadosBancarios", filtro.getDadosBancarios());
		}
	}
	
}

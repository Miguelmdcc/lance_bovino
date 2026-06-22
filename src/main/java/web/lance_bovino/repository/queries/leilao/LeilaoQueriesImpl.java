package web.lance_bovino.repository.queries.leilao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import web.lance_bovino.filter.GadoFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.pagination.PaginacaoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class LeilaoQueriesImpl implements LeilaoQueries {

	@PersistenceContext
	private EntityManager em;

   	public Page<Gado> pesquisar(GadoFilter filtro, Pageable pageable, Long usuarioCodigo) {

		StringBuilder queryGados = new StringBuilder("select distinct g from Gado g");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();

		preencherCondicoesEParametros(filtro, condicoes, parametros);

		if (condicoes.isEmpty()) {
			condicoes.append(" where g.status = 'ATIVO' and g.usuario.codigo = "+usuarioCodigo);
		} else {
			condicoes.append(" and g.status = 'ATIVO' and g.usuario.codigo = "+usuarioCodigo);
		}

		queryGados.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryGados, "g", pageable);
		TypedQuery<Gado> typedQuery = em.createQuery(queryGados.toString(), Gado.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Gado> vacinas = typedQuery.getResultList();

		long totalGados = PaginacaoUtil.getTotalRegistros("Gado", "g", condicoes, parametros, em);

		return new PageImpl<>(vacinas, pageable, totalGados);
	}

	public List<Gado> pesquisarGeral(String filtro) {
		StringBuilder queryGados = new StringBuilder("select distinct g from Gado g");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();
		preencherCondicoesEParametrosString(filtro, condicoes, parametros);
		if (condicoes.isEmpty()) {
			condicoes.append(" where g.status = 'ATIVO'");
		} else {
			condicoes.append(" and g.status = 'ATIVO'");
		}
		queryGados.append(condicoes);
		TypedQuery<Gado> typedQuery = em.createQuery(queryGados.toString(), Gado.class);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Gado> gados = typedQuery.getResultList();
		return gados;
	}

	private void preencherCondicoesEParametrosString(String filtro, StringBuilder condicoes,
			Map<String, Object> parametros) {
		if(filtro != null){
			boolean condicao = false;
			try {
				Long codigo = Long.parseLong(filtro);
				if (!condicao) {
					condicoes.append(" where ");
				} else {
					condicoes.append(" or ");
				}
				condicoes.append("g.codigo = :codigo");
				parametros.put("codigo", codigo);
				condicao = true;
			} catch (NumberFormatException e) {
				if (!condicao) {
					condicoes.append(" where ");
				} else {
					condicoes.append(" or ");
				}
				condicoes.append("lower(g.nome) like :nome");
				parametros.put("nome", "%" + filtro.toLowerCase() + "%");
				condicao = true;
			}
		}
	}

	private void preencherCondicoesEParametros(GadoFilter filtro, StringBuilder condicoes, Map<String, Object> parametros) {
		boolean condicao = false;

		if (filtro.getCodigo() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("g.codigo = :codigo");
			parametros.put("codigo", filtro.getCodigo());
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getNome())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);		
			condicoes.append("lower(g.nome) like :nome");
			parametros.put("nome", "%" + filtro.getNome().toLowerCase() + "%");
			condicao = true;
		}
		if (filtro.getPeso() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("g.peso = :peso");
			parametros.put("peso", filtro.getPeso());
			condicao = true;
		}

		if (filtro.getRaca() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("g.raca = :raca");
			parametros.put("raca", filtro.getRaca());
			condicao = true;
		}
		if (filtro.getAltura() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("g.altura = :altura");
			parametros.put("altura", filtro.getAltura());
			condicao = true;
		}
		if (filtro.getIdade() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("g.idade = :idade");
			parametros.put("idade", filtro.getIdade());
			condicao = true;
		}
	}
	
}

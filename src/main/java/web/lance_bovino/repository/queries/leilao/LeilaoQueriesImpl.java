package web.lance_bovino.repository.queries.leilao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import web.lance_bovino.filter.LeilaoFilter;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.pagination.PaginacaoUtil;

public class LeilaoQueriesImpl implements LeilaoQueries {

	@PersistenceContext
	private EntityManager em;

   	public Page<Leilao> pesquisar(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo) {

		StringBuilder queryLeilao = new StringBuilder("select distinct l from Leilao l");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();

		preencherCondicoesEParametros(filtro, condicoes, parametros);

		if (condicoes.isEmpty()) {
			condicoes.append(" where l.ativo = true and l.usuario.codigo = "+usuarioCodigo);
		} else {
			condicoes.append(" and l.ativo = true and l.usuario.codigo = "+usuarioCodigo);
		}

		queryLeilao.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryLeilao, "l", pageable);
		TypedQuery<Leilao> typedQuery = em.createQuery(queryLeilao.toString(), Leilao.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Leilao> leiloes = typedQuery.getResultList();

		long totalLeiloes = PaginacaoUtil.getTotalRegistros("Leilao", "l", condicoes, parametros, em);

		return new PageImpl<>(leiloes, pageable, totalLeiloes);
	}

	public List<Leilao> pesquisarGeral(String filtro) {
		StringBuilder queryLeiloes = new StringBuilder("select distinct l from Leilao l");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();
		preencherCondicoesEParametrosString(filtro, condicoes, parametros);
		if (condicoes.isEmpty()) {
			condicoes.append(" where l.ativo = true and l.status = 'ABERTO'");
		} else {
			condicoes.append(" and l.ativo = true and l.status = 'ABERTO'");
		}
		queryLeiloes.append(condicoes);
		TypedQuery<Leilao> typedQuery = em.createQuery(queryLeiloes.toString(), Leilao.class);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Leilao> leiloes = typedQuery.getResultList();
		return leiloes;
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
				condicoes.append("l.codigo = :codigo");
				parametros.put("codigo", codigo);
				condicao = true;
			} catch (NumberFormatException e) {
				if (!condicao) {
					condicoes.append(" where ");
				} else {
					condicoes.append(" or ");
				}
				condicoes.append("lower(l.nome) like :nome");
				parametros.put("nome", "%" + filtro.toLowerCase() + "%");
				condicao = true;
			}
		}
	}

	private void preencherCondicoesEParametros(LeilaoFilter filtro, StringBuilder condicoes, Map<String, Object> parametros) {
		boolean condicao = false;

		if (filtro.getCodigo() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.codigo = :codigo");
			parametros.put("codigo", filtro.getCodigo());
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getNome())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);		
			condicoes.append("lower(l.nome) like :nome");
			parametros.put("nome", "%" + filtro.getNome().toLowerCase() + "%");
			condicao = true;
		}
		if (filtro.getInitialPrice() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.initialPrice = :initialPrice");
			parametros.put("initialPrice", filtro.getInitialPrice());
			condicao = true;
		}

		if (filtro.getFinalTimestamp() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.final_timestamp = :final_timestamp");
			parametros.put("final_timestamp", filtro.getFinalTimestamp());
			condicao = true;
		}
		if (filtro.getUsuario() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.usuario = :usuario");
			parametros.put("usuario", filtro.getUsuario());
			condicao = true;
		}
		if (filtro.getGado() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.gado = :gado");
			parametros.put("gado", filtro.getGado());
			condicao = true;
		}
		if (filtro.getStatus() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.status = :status");
			parametros.put("status", filtro.getStatus());
			condicao = true;
		}
	}

	public List<Leilao> atualizarStatusLeiloes() {
		LocalDateTime agora = LocalDateTime.now();
		String selectQuery = "select l from Leilao l where l.finalTimestamp < :agora and l.status = :statusAberto";
		List<Leilao> leiloes = em.createQuery(selectQuery, Leilao.class)
				.setParameter("agora", agora)
				.setParameter("statusAberto", StatusLeilao.ABERTO)
				.getResultList();

		if (!leiloes.isEmpty()) {
			String updateQuery = "UPDATE Leilao l SET l.status = :novoStatus WHERE l.finalTimestamp < :agora AND l.status = :statusAberto";

			em.createQuery(updateQuery)
				.setParameter("novoStatus", StatusLeilao.ENCERRADO)
				.setParameter("agora", agora)
				.setParameter("statusAberto", StatusLeilao.ABERTO)
				.executeUpdate();

			for (Leilao leilao : leiloes) {
				em.refresh(leilao);
			}
		}

		return leiloes;
	}

	public Page<Leilao> pesquisarLeiloes(LeilaoFilter filtro, Pageable pageable, Long usuarioCodigo) {

		StringBuilder queryLeilao = new StringBuilder("select distinct l from Leilao l");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();

		preencherCondicoesEParametros(filtro, condicoes, parametros);

		if (condicoes.isEmpty()) {
			condicoes.append(" where l.ativo = true and l.status = 'ABERTO' and l.usuario.codigo != "+usuarioCodigo);
		} else {
			condicoes.append(" and l.ativo = true and l.status = 'ABERTO' and l.usuario.codigo != "+usuarioCodigo);
		}

		queryLeilao.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryLeilao, "l", pageable);
		TypedQuery<Leilao> typedQuery = em.createQuery(queryLeilao.toString(), Leilao.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Leilao> leiloes = typedQuery.getResultList();

		long totalLeiloes = PaginacaoUtil.getTotalRegistros("Leilao", "l", condicoes, parametros, em);

		return new PageImpl<>(leiloes, pageable, totalLeiloes);
	}
	
}

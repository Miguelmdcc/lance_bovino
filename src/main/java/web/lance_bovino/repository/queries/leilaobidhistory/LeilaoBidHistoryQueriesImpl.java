package web.lance_bovino.repository.queries.leilaobidhistory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import web.lance_bovino.filter.LeilaoBidHistoryFilter;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.GadoHistory;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.LeilaoBidHistory;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.pagination.PaginacaoUtil;

public class LeilaoBidHistoryQueriesImpl implements LeilaoBidHistoryQueries {

	@PersistenceContext
	private EntityManager em;

   	public Page<LeilaoBidHistory> pesquisar(LeilaoBidHistoryFilter filtro, Pageable pageable, Long usuarioCodigo) {

		StringBuilder queryLeilaoBH = new StringBuilder("select distinct lbh from LeilaoBidHistory lbh");
		StringBuilder condicoes = new StringBuilder();
		Map<String, Object> parametros = new HashMap<>();

		preencherCondicoesEParametros(filtro, condicoes, parametros);

		if (condicoes.isEmpty()) {
			condicoes.append(" where lbh.usuario.codigo = "+usuarioCodigo);
		} else {
			condicoes.append(" and lbh.usuario.codigo = "+usuarioCodigo);
		}

		String vencedorString = filtro.getVencedor();
		if(vencedorString != null && !vencedorString.isBlank()){
			if("Venceu".equalsIgnoreCase(vencedorString)){
				condicoes.append(" AND lbh.leilao.vencedor.codigo = :codigoVencedor ");
        		parametros.put("codigoVencedor", usuarioCodigo);
			}	
			if("Não venceu".equalsIgnoreCase(vencedorString)){
				condicoes.append(" AND lbh.leilao.vencedor is null");
			}
		}

		queryLeilaoBH.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryLeilaoBH, "lbh", pageable);
		TypedQuery<LeilaoBidHistory> typedQuery = em.createQuery(queryLeilaoBH.toString(), LeilaoBidHistory.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<LeilaoBidHistory> leiloes = typedQuery.getResultList();

		long totalLeiloes = PaginacaoUtil.getTotalRegistros("LeilaoBidHistory", "lbh", condicoes, parametros, em);

		return new PageImpl<>(leiloes, pageable, totalLeiloes);
	}

	// private void preencherCondicoesEParametrosString(String filtro, StringBuilder condicoes,
	// 		Map<String, Object> parametros) {
	// 	if(filtro != null){
	// 		boolean condicao = false;
	// 		try {
	// 			Long codigo = Long.parseLong(filtro);
	// 			if (!condicao) {
	// 				condicoes.append(" where ");
	// 			} else {
	// 				condicoes.append(" or ");
	// 			}
	// 			condicoes.append("l.codigo = :codigo");
	// 			parametros.put("codigo", codigo);
	// 			condicao = true;
	// 		} catch (NumberFormatException e) {
	// 			if (!condicao) {
	// 				condicoes.append(" where ");
	// 			} else {
	// 				condicoes.append(" or ");
	// 			}
	// 			condicoes.append("lower(l.nome) like :nome");
	// 			parametros.put("nome", "%" + filtro.toLowerCase() + "%");
	// 			condicao = true;
	// 		}
	// 	}
	// }

	private void preencherCondicoesEParametros(LeilaoBidHistoryFilter filtro, StringBuilder condicoes, Map<String, Object> parametros) {
		boolean condicao = false;

		if (filtro.getCodigo() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.codigo = :codigo ");
			parametros.put("codigo", filtro.getCodigo());
			condicao = true;
		}
		
		if (StringUtils.hasText(filtro.getNome())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);       
			condicoes.append(" lower(lbh.leilao.nome) like :nome ");
			parametros.put("nome", "%" + filtro.getNome().toLowerCase() + "%");
			condicao = true;
		}
		
		if (filtro.getLeilao() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.leilao = :leilao ");
			parametros.put("leilao", filtro.getLeilao());
			condicao = true;
		}
		
		if (filtro.getGado() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.leilao.gado = :gado ");
			parametros.put("gado", filtro.getGado());
			condicao = true;
		}

		if (filtro.getUsuario() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.usuario = :usuario ");
			parametros.put("usuario", filtro.getUsuario());
			condicao = true;
		}
		
		if (filtro.getBidValue() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.bidValue = :lance ");
			parametros.put("lance", filtro.getBidValue());
			condicao = true;
		}
		
		if (filtro.getTimestampDeCriacao() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.timestampDeCriacao = :criacao ");
			parametros.put("criacao", filtro.getTimestampDeCriacao());
			condicao = true;
		}
		
		if (filtro.getStatus() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append(" lbh.leilao.status = :status ");
			parametros.put("status", filtro.getStatus());
			condicao = true;
		}
	}

	@Override
	public LeilaoBidHistory buscarUltimoLance(Long codigo) {
		String leilaoBHQuery = "SELECT lbh FROM LeilaoBidHistory lbh " +
							"WHERE lbh.leilao.codigo = :codigoLeilao " +
							"ORDER BY lbh.bidValue DESC";
		
		List<LeilaoBidHistory> lances = em.createQuery(leilaoBHQuery, LeilaoBidHistory.class)
				.setParameter("codigoLeilao", codigo)
				.setMaxResults(1)
				.getResultList();
				
		return lances.isEmpty() ? null : lances.get(0);
	}
	
	@Override
	@Transactional 
	public void atualizarVencedores(List<Leilao> leiloesEncerrados){
		for (Leilao leilao : leiloesEncerrados) {
			Long codigo = leilao.getCodigo();
			
			String leilaoBHQuery = "SELECT lbh FROM LeilaoBidHistory lbh " +
					"WHERE lbh.leilao.codigo = :codigoLeilao " +
					"ORDER BY lbh.bidValue DESC";
					
			List<LeilaoBidHistory> lances = em.createQuery(leilaoBHQuery, LeilaoBidHistory.class)
				.setParameter("codigoLeilao", codigo)
				.setMaxResults(1)
				.getResultList();
				
			if (!lances.isEmpty()) {
				Usuario usuarioVencedor = lances.get(0).getUsuario();
				
				leilao.setVencedor(usuarioVencedor);
				em.merge(leilao);
				
				Gado gado = leilao.getGado();
				if (gado != null) {
					gado.setUsuario(usuarioVencedor); 
					em.merge(gado);
					
					GadoHistory historico = new GadoHistory();
					historico.setGado(gado);
					historico.setUsuario(usuarioVencedor); 
					historico.setTimestampDeCriacao(LocalDateTime.now());
					historico.setAtivo(true); 
					
					em.persist(historico); 
				}
			}
		}
	}
}

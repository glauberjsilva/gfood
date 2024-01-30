package com.glauber.gfood.infrastructure.respository;

import static com.glauber.gfood.infrastructure.respository.spec.RestauranteSpecs.comFreteGratis;
import static com.glauber.gfood.infrastructure.respository.spec.RestauranteSpecs.comNomesSemelhantes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.glauber.gfood.domain.model.Restaurante;
import com.glauber.gfood.domain.repository.RestauranteRepository;
import com.glauber.gfood.domain.repository.RestauranteRepositoryQueries;



@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	//Criteria para filtros dinâmicos
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}

		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}

		if (taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(comNomesSemelhantes(nome)));
	}

	
	//JPQL com filtros dinâmicos
	/*
	 * public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial,
	 * BigDecimal taxaFreteFinal) { var jpql = new StringBuilder();
	 * 
	 * var parametros = new HashMap<String, Object>();
	 * 
	 * jpql.append("from Restaurante where 0 = 0 ");
	 * 
	 * if (StringUtils.hasLength(nome)) { jpql.append("and nome like :nome ");
	 * parametros.put("nome", "%" + nome + "%"); }
	 * 
	 * if (taxaFreteInicial != null) {
	 * jpql.append("and taxaFrete >= :taxaInicial "); parametros.put("taxaInicial",
	 * taxaFreteInicial); }
	 * 
	 * if (taxaFreteFinal != null) { jpql.append("and taxaFrete <= :taxaFinal ");
	 * parametros.put("taxaFinal", taxaFreteFinal); }
	 * 
	 * TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(),
	 * Restaurante.class);
	 * 
	 * parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
	 * 
	 * return query.getResultList(); }
	 */

	//JPQL com filtros dinâmicos 2
	/*
	 * @Override public Stream<Restaurante> find(String nome, BigDecimal
	 * taxaFreteInicial, BigDecimal taxaFreteFinal) { var jpql = "from Restaurante "
	 * + " where 0=0 " + " and (:nome is null or nome like :nome) " +
	 * " and (:taxaInicial is null or taxaFrete >= :taxaInicial)" +
	 * " and (:taxaFinal is null or taxaFrete <= :taxaFinal) ";
	 * 
	 * return manager.createQuery(jpql, Restaurante.class).setParameter("taxaFinal",
	 * taxaFreteFinal) .setParameter("taxaInicial",
	 * taxaFreteInicial).setParameter("nome", "%" + nome + "%").getResultStream(); }
	 */
	 
}

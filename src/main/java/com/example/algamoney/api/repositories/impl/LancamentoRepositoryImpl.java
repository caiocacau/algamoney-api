package com.example.algamoney.api.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.util.ObjectUtils;

import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.entities.Lancamento_;
import com.example.algamoney.api.repositories.filters.LancamentoFilter;
import com.example.algamoney.api.repositories.queries.LancamentoRepositoryQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
		List<Lancamento> listLancamentos = query.getResultList();
		listLancamentos.forEach((lancamento)-> Hibernate.initialize(lancamento.getPessoa()));
//		for (Lancamento lancamento : listLancamentos) {
//			Hibernate.initialize(lancamento.getPessoa());
//		}
		return listLancamentos;
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
				
					/**
					 * Curso no slide 5.7 implementando pesquisa de lancamento com metamodel v1
					 * explica com gerar. Aproximadamento Minuto 18 da aula 
					 * Para gerar as Entities Categoria_.java, Endereco_.java, Lancamento_.java, etc
					 */	
					
//					builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
					builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%")
			);
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
//					builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe())
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe())
			);
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
//					builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte())
					builder.lessThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoAte())
			);
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}

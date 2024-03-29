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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import com.example.algamoney.api.entities.Categoria_;
import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.entities.Lancamento_;
import com.example.algamoney.api.entities.Pessoa_;
import com.example.algamoney.api.repositories.filters.LancamentoFilter;
import com.example.algamoney.api.repositories.projections.ResumoLancamento;
import com.example.algamoney.api.repositories.queries.LancamentoRepositoryQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
		
		TypedQuery<Lancamento> query = extractedToCriteria(lancamentoFilter);
		List<Lancamento> listLancamentos = query.getResultList();
		
		return listLancamentos;
	}

	@Override
	public Page<Lancamento> filtrarPaginado(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		TypedQuery<Lancamento> query = extractedToCriteria(lancamentoFilter);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		// builder.construct -> está retornando o resultado diretamente pelo construtor da classe
		criteria.select(builder.construct(ResumoLancamento.class, 
				root.get(Lancamento_.codigo),
				root.get(Lancamento_.descricao),
				root.get(Lancamento_.dataVencimento),
				root.get(Lancamento_.dataPagamento),
				root.get(Lancamento_.valor),
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.categoria).get(Categoria_.nome),
				root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
				
		TypedQuery<ResumoLancamento> query = entityManager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	private TypedQuery<Lancamento> extractedToCriteria(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
		return query;
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
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		// builder.count(root) é o mesmo que o Count *
		criteria.select(builder.count(root));
		
		return entityManager.createQuery(criteria).getSingleResult();
	}

}

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
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.ObjectUtils;

import com.example.algamoney.api.entities.Usuario;
import com.example.algamoney.api.repositories.filters.UsuarioFilter;
import com.example.algamoney.api.repositories.queries.UsuarioRepositoryQuery;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Usuario> filtrarPaginado(UsuarioFilter usuarioFilter, Pageable pageable) {
		
		TypedQuery<Usuario> query = extractedToCriteria(usuarioFilter, pageable);
		adicionarRestricoesDePaginacao(query, pageable);
	
		return new PageImpl<>(query.getResultList(), pageable, total(usuarioFilter));
	}
	
	private TypedQuery<Usuario> extractedToCriteria(UsuarioFilter usuarioFilter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(usuarioFilter, builder, root);
		criteria.where(predicates);
		
		criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));
		
		TypedQuery<Usuario> query = entityManager.createQuery(criteria);
		return query;
	}

	private Predicate[] criarRestricoes(UsuarioFilter usuarioFilter, CriteriaBuilder builder,
			Root<Usuario> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (!ObjectUtils.isEmpty(usuarioFilter.getCodigo())) {
			predicates.add(builder.equal(root.get("codigo"),usuarioFilter.getCodigo()));
		} else {
			
			if (!ObjectUtils.isEmpty(usuarioFilter.getNome())) {
				predicates.add(builder.like(
					
						/**
						 * Curso no slide 5.7 implementando pesquisa de lancamento com metamodel v1
						 * explica com gerar. Aproximadamento Minuto 18 da aula 
						 * Para gerar as Entities Categoria_.java, Endereco_.java, Lancamento_.java, etc
						 */	
						
						builder.lower(root.get("nome")), "%" + usuarioFilter.getNome().toLowerCase() + "%")
	//					builder.lower(root.get(Usuario_.nome)), "%" + usuarioFilter.getNome().toLowerCase() + "%")
				);
			}
			
			if (!ObjectUtils.isEmpty(usuarioFilter.getEmail())) {
				predicates.add(builder.like(
						builder.lower(root.get("email")), "%" + usuarioFilter.getEmail().toLowerCase() + "%")
	//					builder.lower(root.get(Usuario_.email)), "%" + usuarioFilter.getEmail().toLowerCase() + "%")
				);
			}
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
	
	private Long total(UsuarioFilter usuarioFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(usuarioFilter, builder, root);
		criteria.where(predicates);

		// builder.count(root) é o mesmo que o Count *
		criteria.select(builder.count(root));
		
		return entityManager.createQuery(criteria).getSingleResult();
	}

}

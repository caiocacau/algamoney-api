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

import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.entities.Pessoa_;
import com.example.algamoney.api.repositories.filters.PessoaFilter;
import com.example.algamoney.api.repositories.queries.PessoaRepositoryQuery;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Pessoa> carregarPessoasFetchLancamentos(PessoaFilter pessoaFilter) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
//		root.join(Pessoa_.lancamentos);
		
		// Criar as restrições
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		
		criteria.select(root);
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = entityManager.createQuery(criteria);
		List<Pessoa> listPessoas = query.getResultList();
		listPessoas.forEach((pessoa) -> {
				Hibernate.initialize(pessoa.getLancamentos());
				Hibernate.initialize(pessoa.getAlcunhas());
			});
		return listPessoas;
	}

	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder,
			Root<Pessoa> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (!ObjectUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(
				
					/**
					 * Curso no slide 5.7 implementando pesquisa de lancamento com metamodel v1
					 * explica com gerar. Aproximadamento Minuto 18 da aula 
					 * Para gerar as Entities Categoria_.java, Endereco_.java, Lancamento_.java, etc
					 */	
					
//					builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
					builder.lower(root.get(Pessoa_.nome)), "%" + pessoaFilter.getNome().toLowerCase() + "%")
			);
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}

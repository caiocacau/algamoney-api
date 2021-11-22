package com.example.algamoney.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.repositories.queries.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
	
}
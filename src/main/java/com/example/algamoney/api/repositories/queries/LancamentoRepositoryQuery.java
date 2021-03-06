package com.example.algamoney.api.repositories.queries;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.repositories.filters.LancamentoFilter;
import com.example.algamoney.api.repositories.projections.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
	public Page<Lancamento> filtrarPaginado(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}

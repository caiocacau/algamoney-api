package com.example.algamoney.api.repositories.queries;

import java.util.List;

import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.repositories.filters.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}

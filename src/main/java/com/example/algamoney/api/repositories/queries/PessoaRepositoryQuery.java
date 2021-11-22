package com.example.algamoney.api.repositories.queries;

import java.util.List;

import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.repositories.filters.PessoaFilter;

public interface PessoaRepositoryQuery {

	public List<Pessoa> carregarPessoasFetchLancamentos(PessoaFilter pessoaFilter);
	
}

package com.example.algamoney.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.repositories.LancamentoRepository;
import com.example.algamoney.api.services.exceptions.PessoaInativaException;
import com.example.algamoney.api.services.exceptions.PessoaInexistenteException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;

	public Lancamento findById(Long codigo) {
		return buscarLancamentoPeloCodigo(codigo);
	}

	private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
		if (!lancamento.isPresent()) { 
			throw new EmptyResultDataAccessException(1);
		}
		return lancamento.get(); 
	}

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = null;
		try {
			pessoa = pessoaService.buscarPessoaPeloCodigo(lancamento.getPessoa().getCodigo());
		} catch (Exception e) {
			throw new PessoaInexistenteException(e.getMessage());
		}
		
		if (pessoa.isInativo()) {
			throw new PessoaInativaException();
		}

		return lancamentoRepository.save(lancamento);
	}

}

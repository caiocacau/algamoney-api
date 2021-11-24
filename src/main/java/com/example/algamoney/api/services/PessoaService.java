package com.example.algamoney.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.entities.Alcunha;
import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.repositories.AlcunhaRepository;
import com.example.algamoney.api.repositories.LancamentoRepository;
import com.example.algamoney.api.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private AlcunhaRepository alcunhaRepository;
	
	public Pessoa findById(Long codigo) {
		return buscarPessoaPeloCodigo(codigo);
	}
	
	public Pessoa inserir(Pessoa pessoa) {
		Pessoa pessoaInserir = new Pessoa();
		
		BeanUtils.copyProperties(pessoa, pessoaInserir, new String[] {"alcunhas","lancamentos"});
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoaInserir);
		
		if (!pessoa.getLancamentos().isEmpty()) {
			List<Lancamento> lancamentos = pessoa.getLancamentos();
			lancamentos.forEach((lancamento)-> lancamento.setPessoa(pessoaSalva));
			List<Lancamento> listLancamentos = lancamentoRepository.saveAll(lancamentos);
			pessoaSalva.setLancamentos(listLancamentos);
		}
		
		if (!pessoa.getAlcunhas().isEmpty()) {
			List<Alcunha> alcunhas = pessoa.getAlcunhas();
			alcunhas.forEach((alcunha)-> alcunha.setPessoa(pessoaSalva));
			List<Alcunha> listAlcunhas = alcunhaRepository.saveAll(alcunhas);
			pessoaSalva.setAlcunhas(listAlcunhas);
		}
		
		return pessoaSalva;
	}
	
	// Outra forma de inserir Pessoa
	public Pessoa inserirVersao2(Pessoa pessoa) {
		
		atualizandoListasComPessoa(pessoa);
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		return pessoaSalva;
	}
	
	@Transactional
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		
		Pessoa pessoaBD = buscarPessoaPeloCodigo(codigo);
		
		List<Long> idLancamentosARemover = idsLancamentosARemover(pessoa, pessoaBD);
		lancamentoRepository.deleteAllById(idLancamentosARemover);

		List<Long> idAlcunhasARemover = idsAlcunhasARemover(pessoa, pessoaBD);
		alcunhaRepository.deleteAllById(idAlcunhasARemover);
		
		BeanUtils.copyProperties(pessoa, pessoaBD, "codigo");
		atualizandoListasComPessoa(pessoaBD);
		
		return pessoaRepository.save(pessoaBD);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoa = buscarPessoaPeloCodigo(codigo);
		pessoa.setAtivo(ativo);
		pessoaRepository.save(pessoa);
	}
	
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		if (!pessoa.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoa.get();
	}
	
	private void atualizandoListasComPessoa(Pessoa pessoa) {
		if (!pessoa.getLancamentos().isEmpty()) {
			pessoa.getLancamentos().forEach((lancamento)-> {
				lancamento.setPessoa(pessoa);
			});
		}
			
		if (!pessoa.getAlcunhas().isEmpty()) {
			pessoa.getAlcunhas().forEach((alcunha)-> {
				alcunha.setPessoa(pessoa);
			});
		}
	}
	
	private List<Long> idsLancamentosARemover(Pessoa pessoa, Pessoa pessoaBD) {
		// Excluindo registros que não tiverem nas listas
		List<Long> idLancamentosARemover = new ArrayList<>();
		
		pessoaBD.getLancamentos().forEach( lancamento -> {
			if (!pessoa.getLancamentos().contains(lancamento)) {
				idLancamentosARemover.add(lancamento.getCodigo());
			};
		});
		
//		for (Lancamento lancamento : pessoaBD.getLancamentos()) {
//			if (lancamento.getCodigo() != null) {
//				if (!pessoa.getLancamentos().contains(lancamento)) {
//					idLancamentosARemover.add(lancamento.getCodigo());
//				};
//			}
//		}
		return idLancamentosARemover;
	}
	
	private List<Long> idsAlcunhasARemover(Pessoa pessoa, Pessoa pessoaBD) {
		// Excluindo registros que não tiverem nas listas
		List<Long> idAlcunhasARemover = new ArrayList<>();
		
		pessoaBD.getAlcunhas().forEach( alcunha -> {
			if (!pessoa.getAlcunhas().contains(alcunha)) {
				idAlcunhasARemover.add(alcunha.getCodigo());
			};
		});
		
//		for (Alcunha alcunha : pessoaBD.getAlcunhas()) {
//			if (alcunha.getCodigo() != null) {
//				if (!pessoa.getAlcunhas().contains(alcunha)) {
//					idAlcunhasARemover.add(alcunha.getCodigo());
//				};
//			}
//		}
		return idAlcunhasARemover;
	}
	
}

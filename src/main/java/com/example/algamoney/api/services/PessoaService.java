package com.example.algamoney.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.entities.Alcunha;
import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.repositories.AlcunhaRepository;
import com.example.algamoney.api.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private AlcunhaRepository alcunhaRepository;
	
	public Pessoa findById(Long codigo) {
		return buscarPessoaPeloCodigo(codigo);
	}
	
	public Pessoa inserir(Pessoa pessoa) {
		Pessoa pessoaInserir = new Pessoa();
		BeanUtils.copyProperties(pessoa, pessoaInserir, "alcunhas");
		Pessoa pessoaSalva = pessoaRepository.save(pessoaInserir);
		
		if (!pessoa.getAlcunhas().isEmpty()) {
			List<Alcunha> alcunhas = pessoa.getAlcunhas();
			alcunhas.forEach((alcunha)-> alcunha.setPessoa(pessoaSalva));
			List<Alcunha> listAlcunhas = alcunhaRepository.saveAll(alcunhas);
			pessoaSalva.setAlcunhas(listAlcunhas);
		}
		
		return pessoaSalva;
	}
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaRepository.save(pessoaSalva);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
	
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		if (!pessoa.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoa.get();
	}
	
}

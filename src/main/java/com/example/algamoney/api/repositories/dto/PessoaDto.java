package com.example.algamoney.api.repositories.dto;

import java.util.List;

import org.hibernate.Hibernate;

import com.example.algamoney.api.entities.Alcunha;
import com.example.algamoney.api.entities.Endereco;
import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.entities.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDto {
	
	private Long codigo;
	private String nome;
	private Endereco endereco;
	private Boolean ativo;
	private List<Lancamento> lancamentos;
	private List<Alcunha> alcunhas;
	
	public Pessoa transformToEntity() {
		return new Pessoa(codigo, nome, endereco, ativo, lancamentos, alcunhas);
	}
	
	public PessoaDto transformToDto(Pessoa pessoa) {
		
		Hibernate.initialize(pessoa.getLancamentos());
		Hibernate.initialize(pessoa.getAlcunhas());
		
		return new PessoaDto(
				pessoa.getCodigo(),
				pessoa.getNome(),
				pessoa.getEndereco(),
				pessoa.getAtivo(),
				pessoa.getLancamentos(),
				pessoa.getAlcunhas());
	}
	
}

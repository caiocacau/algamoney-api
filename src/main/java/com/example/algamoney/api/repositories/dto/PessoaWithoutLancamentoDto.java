package com.example.algamoney.api.repositories.dto;

import java.util.List;

import org.hibernate.Hibernate;

import com.example.algamoney.api.entities.Alcunha;
import com.example.algamoney.api.entities.Endereco;
import com.example.algamoney.api.entities.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaWithoutLancamentoDto {
	
	private Long codigo;
	private String nome;
	private Endereco endereco;
	private Boolean ativo;
	private List<Alcunha> alcunhas;
	
	public PessoaWithoutLancamentoDto transformToDto(Pessoa pessoa) {
		
		Hibernate.initialize(pessoa.getAlcunhas());
		
		return new PessoaWithoutLancamentoDto(
				pessoa.getCodigo(),
				pessoa.getNome(),
				pessoa.getEndereco(),
				pessoa.getAtivo(),
				pessoa.getAlcunhas());
	}
	
}

package com.example.algamoney.api.repositories.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.Hibernate;

import com.example.algamoney.api.entities.Categoria;
import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.entities.enumerations.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDto {
	
	private Long codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private String observacao;
	private TipoLancamento tipo;
	private Categoria categoria;
	
	@JsonIgnore
	private Pessoa pessoa;
	
	@JsonProperty("pessoa")
	private PessoaWithoutLancamentoDto pessoaI;

	public Lancamento transformToEntity() {
		return new Lancamento(codigo, descricao, dataVencimento, dataPagamento, valor, observacao, tipo, categoria, pessoa);
	}
	
	public LancamentoDto transformToDto(Lancamento lancamento) {
		
		Hibernate.initialize(lancamento.getPessoa());
		Pessoa pessoa = lancamento.getPessoa();
		Hibernate.initialize(pessoa.getAlcunhas());
		
		return new LancamentoDto(
				lancamento.getCodigo(), 
				lancamento.getDescricao(),
				lancamento.getDataVencimento(),
				lancamento.getDataPagamento(),
				lancamento.getValor(),
				lancamento.getObservacao(),
				lancamento.getTipo(),
				lancamento.getCategoria(),
				null,
				new PessoaWithoutLancamentoDto(pessoa.getCodigo(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getAtivo(), pessoa.getAlcunhas()));
	}
	
}

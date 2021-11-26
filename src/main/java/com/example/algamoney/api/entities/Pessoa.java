package com.example.algamoney.api.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	
	@NotNull
	@Size(min = 3, max = 60)
	private String nome;
	
	@Embedded
	private Endereco endereco;

	@NotNull
	private Boolean ativo;
	
	@OneToMany(mappedBy = "pessoa",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Lancamento> lancamentos;

	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Alcunha> alcunhas;
	
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
	}
	
	public void adiconarLancamento(Lancamento lancamento) {
		if (lancamento != null) {
			if (this.lancamentos == null)
				lancamentos = new ArrayList<>();
			
			if (lancamento.getPessoa() == null)
				lancamento.setPessoa(this);

			lancamentos.add(lancamento);
		}
	}

}

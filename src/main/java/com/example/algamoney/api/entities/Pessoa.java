package com.example.algamoney.api.entities;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	
	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("pessoa")
//	@JsonManagedReference
	private List<Lancamento> lancamentos;
	
	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("pessoa")
//	@JsonManagedReference
	private List<Alcunha> alcunhas;
	
//	@OneToMany(targetEntity=Lancamento.class)
//	@JoinTable(name="tb_lancamento",
//	joinColumns={ @JoinColumn(name="codigo", referencedColumnName="codigo") },
//	inverseJoinColumns={ @JoinColumn(name="codigo_pessoa", referencedColumnName="codigo", unique=true) }
//	)
//	private List<Lancamento> lancamentos;
	
//	@OneToMany(targetEntity=Lancamento.class)
//	@JoinColumn(name="codigo_pessoa")
//	private List<Lancamento> lancamentos;
	
//	@JsonIgnore
//	public List<Lancamento> getLancamentos() {
//		return lancamentos == null ? new ArrayList<Lancamento>() : lancamentos;
//	}

	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
	}

}

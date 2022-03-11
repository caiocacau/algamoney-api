package com.example.algamoney.api.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String nome;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String email;

//	Retirado por enquanto devido a regra adotada no frontend(alteração)
//	@NotNull
//	@Size(min = 3, max = 150)
	private String senha;
	
	@NotNull
	@Size(max = 1)
	private String ativo;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
	private List<Permissao> permissoes;
}

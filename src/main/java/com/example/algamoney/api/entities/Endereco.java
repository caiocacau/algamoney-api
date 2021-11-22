package com.example.algamoney.api.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
	
	@Size(max = 100)
	private String logradouro;
	
//	@Column(length = 5)
	@Size(max = 5)
	private String numero;
	
	@Size(max = 30)
	private String complemento;
	
	@Size(max = 30)
	private String bairro;
	
	@Size(max = 10)
	private String cep;
	
	@Size(max = 30)
	private String cidade;
	
	@Size(max = 2)
	private String estado;

}

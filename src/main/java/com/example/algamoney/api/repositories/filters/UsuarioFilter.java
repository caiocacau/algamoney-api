package com.example.algamoney.api.repositories.filters;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter {
	
	private Long codigo;
	
	private String nome;
	
	private String email;
	
	private List<String> ativos;
	
}

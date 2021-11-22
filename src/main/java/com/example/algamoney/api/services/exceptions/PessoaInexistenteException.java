package com.example.algamoney.api.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PessoaInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String msg;

}

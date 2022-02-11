package com.example.algamoney.api.repositories.queries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.entities.Usuario;
import com.example.algamoney.api.repositories.filters.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public Page<Usuario> filtrarPaginado(UsuarioFilter usuarioFilter, Pageable pageable);
	
}

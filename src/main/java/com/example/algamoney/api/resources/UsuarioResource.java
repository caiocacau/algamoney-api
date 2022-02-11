package com.example.algamoney.api.resources;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.entities.Usuario;
import com.example.algamoney.api.repositories.UsuarioRepository;
import com.example.algamoney.api.repositories.filters.UsuarioFilter;

@RestController
@RequestMapping("/users")
//@CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" }) // Pode ser aplicado em classe e também se preferir na aplicação
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Jeito mais correto pois retorna uma lista vazia se não tiver registros
//	@CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" }) // Pode ser aplicado em método
	@GetMapping
//	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}
	
	@Transactional
	@GetMapping(value = "/filtrarPaginado")
//	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		return usuarioRepository.filtrarPaginado(usuarioFilter, pageable);
	}

}
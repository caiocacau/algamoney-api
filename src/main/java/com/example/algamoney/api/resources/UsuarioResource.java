package com.example.algamoney.api.resources;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.entities.Usuario;
import com.example.algamoney.api.events.RecursoCriadoEvent;
import com.example.algamoney.api.repositories.UsuarioRepository;
import com.example.algamoney.api.repositories.filters.UsuarioFilter;
import com.example.algamoney.api.services.UsuarioService;

@RestController
@RequestMapping("/users")
//@CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" }) // Pode ser aplicado em classe e também se preferir na aplicação
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

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

	@GetMapping(value = "/{codigo}")
//	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public Optional<Usuario> detalhar(@PathVariable Long codigo) {
//		return usuarioRepository.findById(codigo);
		return usuarioService.buscarUsuarioPeloCodigo(codigo);
	}
	
	@PostMapping
//	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> inserir(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		Usuario usuarioSalvo = usuarioService.inserir(usuario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	// Atualização total(inclusive listas)
	@Transactional
	@PutMapping("/{codigo}")
//	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @RequestBody @Valid Usuario usuario) {
		Usuario usuarioSalvo = usuarioService.atualizar(codigo, usuario);
		return ResponseEntity.ok(usuarioSalvo);
	}
	
}
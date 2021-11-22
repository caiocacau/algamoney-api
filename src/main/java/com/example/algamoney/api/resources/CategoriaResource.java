package com.example.algamoney.api.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.entities.Categoria;
import com.example.algamoney.api.events.RecursoCriadoEvent;
import com.example.algamoney.api.repositories.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	// Jeito mais correto pois retorna uma lista vazia se não tiver registros
	@GetMapping
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	// Retornando um código de erro, porém, nesse caso para uma lista que estava vazia não é o mais correto
	@GetMapping(value = "/v1")
	public ResponseEntity<?> v1FindAll() {
		List<Categoria> listCategorias = categoriaRepository.findAll();
		return listCategorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listCategorias);
	}

	// @Valid --> Validando as anotações inseridas na entidade
	// Outra forma retornando o objeto Json inserido, não precisa colocar a anotação ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Categoria> inserir(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	// @Valid --> Validando as anotações inseridas na entidade
	// Não retorna o objeto json inserido
	// Colocado a anotação ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/v1")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void v1Inserir(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
	}


	@GetMapping(value = "/{codigo}")
	public Optional<Categoria> detalhar(@PathVariable Long codigo) {
		return categoriaRepository.findById(codigo);
	}

}

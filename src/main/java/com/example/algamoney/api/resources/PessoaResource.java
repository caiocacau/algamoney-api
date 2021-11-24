package com.example.algamoney.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.events.RecursoCriadoEvent;
import com.example.algamoney.api.repositories.PessoaRepository;
import com.example.algamoney.api.repositories.filters.PessoaFilter;
import com.example.algamoney.api.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	// Acessar documentação no endereço
	// https://docs.spring.io/spring-data/jpa/docs/
	// A linha abaixa é a versão desse projeto no POM
	// https://docs.spring.io/spring-data/jpa/docs/2.5.6/reference/html/#reference

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	// Jeito mais correto pois retorna uma lista vazia se não tiver registros
	@Transactional
	@GetMapping
	public List<Pessoa> findAll() {
		List<Pessoa> listPessoas = pessoaRepository.findAll();
		inicializarListas(listPessoas);
		return listPessoas;
	}

	@Transactional
	@GetMapping(value = "/carregarPessoasFetchLancamentos")
	public List<Pessoa> carregarPessoaFetchLancamentos(@RequestBody PessoaFilter pessoaFilter) {
		return pessoaRepository.carregarPessoasFetchLancamentos(pessoaFilter);
	}

	// Retornando um código de erro, porém, nesse caso para uma lista que estava vazia não é o mais correto
	@Transactional
	@GetMapping(value = "/v1")
	public ResponseEntity<?> v1FindAll() {
		List<Pessoa> listPessoas = pessoaRepository.findAll();
		inicializarListas(listPessoas);
		return listPessoas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listPessoas);
	}

	@Transactional
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Pessoa> detalhar(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaService.findById(codigo);
		inicializarObjetos(pessoa);
		return ResponseEntity.ok(pessoa); 
	}

	// @Valid --> Validando as anotações inseridas na entidade
	// Outra forma retornando o objeto Json inserido, não precisa colocar a anotação ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public ResponseEntity<Pessoa> inserir(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		//		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
//		Pessoa pessoaSalva = pessoaService.inserir(pessoa);
		Pessoa pessoaSalva = pessoaService.inserirVersao2(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	// @Valid --> Validando as anotações inseridas na entidade
	// Não retorna o objeto json inserido
	// Colocado a anotação ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/v1")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void v1Inserir(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
	}

	@Transactional
	@GetMapping(value = "findAllPorNome/{nome}")
	public ResponseEntity<?> findAllPorNome(@PathVariable String nome) {
		List<Pessoa> listPessoas = pessoaRepository.findByNome(nome);
		inicializarListas(listPessoas);
		return listPessoas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listPessoas);
	}

	@Transactional
	@GetMapping(value = "findAllPorParteNome/{nome}")
	public ResponseEntity<?> findAllPorParteNome(@PathVariable String nome) {
		List<Pessoa> listPessoas = pessoaRepository.findByNomeContainingIgnoreCase(nome);
		inicializarListas(listPessoas);
		return listPessoas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listPessoas);
	}

	@Transactional
	@GetMapping(value = "findAllPorParteNomeAndEnderecoNumero")
	public ResponseEntity<?> findAllPorParteNomeAndEnderecoNumero(@RequestParam String nome, @RequestParam String enderecoNumero) {
		List<Pessoa> listPessoas = pessoaRepository.findByNomeContainingIgnoreCaseAndEndereco_Numero(nome, enderecoNumero);
		inicializarListas(listPessoas);
		return listPessoas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listPessoas);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT) // 204 - Deu Ok mas não preciso retorna nada
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}

	// Atualização total
	@Transactional
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @RequestBody @Valid Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}

	// Atualização parcial
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT) // 204 - Deu Ok mas não preciso retorna nada
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	
	private void inicializarObjetos(Pessoa pessoa) {
		Hibernate.initialize(pessoa.getLancamentos());
		Hibernate.initialize(pessoa.getAlcunhas());
	}
	
	private void inicializarListas(List<Pessoa> listPessoas) {
		if (!listPessoas.isEmpty()) {
			listPessoas.forEach( pessoa -> {
				inicializarObjetos(pessoa);
			});
		}
	}

}

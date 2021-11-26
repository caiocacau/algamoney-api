package com.example.algamoney.api.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.entities.Lancamento;
import com.example.algamoney.api.events.RecursoCriadoEvent;
import com.example.algamoney.api.exceptions.ExceptionHandler.Erro;
import com.example.algamoney.api.repositories.LancamentoRepository;
import com.example.algamoney.api.repositories.dto.LancamentoDto;
import com.example.algamoney.api.repositories.filters.LancamentoFilter;
import com.example.algamoney.api.services.LancamentoService;
import com.example.algamoney.api.services.exceptions.PessoaInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	// Acessar documentação no endereço
	// https://docs.spring.io/spring-data/jpa/docs/
	// A linha abaixo é a versão desse projeto no POM
	// https://docs.spring.io/spring-data/jpa/docs/2.5.6/reference/html/#reference

	@Autowired
	private LancamentoService lancamentoService;
	
//	@Autowired
//	private PessoaService pessoaService;

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;

	// Jeito mais correto pois retorna uma lista vazia se não tiver registros
	@Transactional
	@GetMapping
	public List<LancamentoDto> findAll() {
		List<Lancamento> list = lancamentoRepository.findAll();
		return extractedToListDto(list);
	}

	@Transactional
	@GetMapping(value = "/pesquisar")
	public List<LancamentoDto> pesquisar(LancamentoFilter lancamentoFilter) {
		List<Lancamento> list = lancamentoRepository.filtrar(lancamentoFilter);
		List<LancamentoDto> listDto = extractedToListDto(list);
		return listDto;
	}

	@Transactional
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<LancamentoDto> detalhar(@PathVariable Long codigo) {
		Lancamento lancamento = lancamentoService.findById(codigo);
		LancamentoDto lancamentoDto = (new LancamentoDto()).transformToDto(lancamento);
		return ResponseEntity.ok(lancamentoDto);
	}
	
	private List<LancamentoDto> extractedToListDto(List<Lancamento> list) {
		List<LancamentoDto> listDto = new ArrayList<>();
		list.forEach( lancamento -> {
			LancamentoDto lancamentoDto = (new LancamentoDto()).transformToDto(lancamento);
			listDto.add(lancamentoDto);
		});
		return listDto;
	}

	// @Valid --> Validando as anotações inseridas na entidade 
	// Outra forma retornando o objeto Json inserido, não precisa colocar a anotação ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Lancamento> inserir(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	// @Valid --> Validando as anotações inseridas na entidade 
	// Não retorna o objeto json inserido 
	// Colocado a anotação ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/v1")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void v1Inserir(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo())); 
	}
	
	/* *****************************************************
	 *
	 * Exceção própria da classe lancamento
	 * Optei em colocar aqui ao invés de colocar na 
	 * ExceptionHandler.java(msgs comuns a todas as classes) 
	 *
	 *******************************************************
	 */
	@ExceptionHandler({ PessoaInativaException.class })
	public ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inativa-para-incluir-lancamento", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
}
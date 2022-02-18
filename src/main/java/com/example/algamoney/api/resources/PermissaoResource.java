package com.example.algamoney.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.entities.Permissao;
import com.example.algamoney.api.repositories.PermissaoRepository;

@RestController
@RequestMapping("/permissoes")
//@CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" }) // Pode ser aplicado em classe e também se preferir na aplicação
public class PermissaoResource {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@GetMapping
	public List<Permissao> findAll() {
		return permissaoRepository.findAll();
	}

}
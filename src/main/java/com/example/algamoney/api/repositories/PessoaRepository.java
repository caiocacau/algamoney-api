package com.example.algamoney.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.entities.Pessoa;
import com.example.algamoney.api.repositories.queries.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

	List<Pessoa> findByNome(String nome);

	List<Pessoa> findByNomeContainingIgnoreCase(String nome); 
	
	List<Pessoa> findByNomeContainingIgnoreCaseAndEndereco_Numero(String nome, String numero);
	
}
package com.example.algamoney.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}

package com.example.algamoney.api.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile SingularAttribute<Pessoa, Long> codigo;
	public static volatile SingularAttribute<Pessoa, Boolean> ativo;
	public static volatile ListAttribute<Pessoa, Lancamento> lancamentos;
	public static volatile SingularAttribute<Pessoa, Endereco> endereco;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile ListAttribute<Pessoa, Alcunha> alcunhas;

	public static final String CODIGO = "codigo";
	public static final String ATIVO = "ativo";
	public static final String LANCAMENTOS = "lancamentos";
	public static final String ENDERECO = "endereco";
	public static final String NOME = "nome";
	public static final String ALCUNHAS = "alcunhas";

}


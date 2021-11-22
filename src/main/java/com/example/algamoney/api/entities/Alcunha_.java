package com.example.algamoney.api.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Alcunha.class)
public abstract class Alcunha_ {

	public static volatile SingularAttribute<Alcunha, Long> codigo;
	public static volatile SingularAttribute<Alcunha, Pessoa> pessoa;
	public static volatile SingularAttribute<Alcunha, String> descricao;

	public static final String CODIGO = "codigo";
	public static final String PESSOA = "pessoa";
	public static final String DESCRICAO = "descricao";

}


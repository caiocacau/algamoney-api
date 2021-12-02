CREATE TABLE tb_usuario (
	codigo BIGSERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
);
	
CREATE TABLE tb_permissao (
	codigo BIGSERIAL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
);
	
CREATE TABLE tb_usuario_permissao (
	codigo_usuario BIGSERIAL NOT NULL,
	codigo_permissao BIGSERIAL NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES tb_usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES tb_permissao(codigo)
);
	
INSERT INTO tb_usuario (codigo, nome, email, senha) values (1, 'Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
	
INSERT INTO tb_permissao (codigo, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO tb_permissao (codigo, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');
	
INSERT INTO tb_permissao (codigo, descricao) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO tb_permissao (codigo, descricao) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO tb_permissao (codigo, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');
	
INSERT INTO tb_permissao (codigo, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO tb_permissao (codigo, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO tb_permissao (codigo, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');
	
-- admin
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 6);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 7);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (1, 8);
	
-- maria
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (2, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (2, 8);
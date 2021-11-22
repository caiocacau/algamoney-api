CREATE TABLE tb_alcunha (
	codigo BIGSERIAL PRIMARY KEY,
	descricao VARCHAR(60) NOT NULL,
	codigo_pessoa BIGINT NOT NULL,
	FOREIGN KEY (codigo_pessoa) REFERENCES tb_pessoa(codigo)
);

INSERT INTO tb_alcunha (descricao, codigo_pessoa) VALUES ('Drexler', 1);
INSERT INTO tb_alcunha (descricao, codigo_pessoa) values ('Kaká', 1);
INSERT INTO tb_alcunha (descricao, codigo_pessoa) values ('Mano', 2);
INSERT INTO tb_alcunha (descricao, codigo_pessoa) values ('Garoto', 4);
CREATE TABLE tb_categoria (
	codigo BIGSERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);

INSERT INTO tb_categoria (nome) VALUES ('Lazer');
INSERT INTO tb_categoria (nome) VALUES ('Alimentação');
INSERT INTO tb_categoria (nome) VALUES ('Supermercado');
INSERT INTO tb_categoria (nome) VALUES ('Farmácia');
INSERT INTO tb_categoria (nome) VALUES ('Outros');
CREATE TABLE tb_pessoa (
	codigo BIGSERIAL PRIMARY KEY,
	nome VARCHAR(60) NOT NULL,
	ativo BOOLEAN NOT NULL,
	logradouro VARCHAR(100),
	numero VARCHAR(5),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(10),
	cidade VARCHAR(30),
	estado VARCHAR(2)
	
);

INSERT INTO tb_pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) VALUES ('Caio','true','Rua Francisco Farias Filho','150','Apto 10','Guararapes','60.810-110','Fortaleza','CE');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Cássio','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Cíntia','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Glaydson','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Deusnúnio','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Cristiano Silva','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Lorena','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Carlos Helder','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Chico da Silva','true');
INSERT INTO tb_pessoa (nome, ativo) VALUES ('Reginaldo Rossi','true');
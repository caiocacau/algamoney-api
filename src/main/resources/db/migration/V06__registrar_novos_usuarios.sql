INSERT INTO tb_usuario (codigo, nome, email, senha) values (3, 'Carlos Hudson', 'carlos@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (4, 'Helder Moura', 'helder@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (5, 'Jairo Brito', 'jairo@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (6, 'Manoel Carvalho', 'manoel@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (7, 'Marilia Freitas', 'marilia@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (8, 'Francisco dos Santos', 'francisco@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (9, 'Pedro Alves', 'pedro@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (10, 'Helena Mara', 'helena@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
INSERT INTO tb_usuario (codigo, nome, email, senha) values (11, 'Joaquim Feitosa', 'joaquim@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');
	
-- usuario de 3 a 11
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (3, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (3, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (3, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (4, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (4, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (4, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (5, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (5, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (5, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (6, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (6, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (6, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (7, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (7, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (7, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (8, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (8, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (8, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (9, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (9, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (9, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (10, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (10, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (10, 8);

INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (11, 2);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (11, 5);
INSERT INTO tb_usuario_permissao (codigo_usuario, codigo_permissao) values (11, 8);
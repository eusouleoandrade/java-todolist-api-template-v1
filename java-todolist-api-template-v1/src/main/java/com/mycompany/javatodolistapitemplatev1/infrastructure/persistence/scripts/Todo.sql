use todolistdb

CREATE TABLE todo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    done BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO todo (title, done) VALUES ('Comprar leite', false);
INSERT INTO todo (title, done) VALUES ('Fazer exercícios', false);
INSERT INTO todo (title, done) VALUES ('Estudar programação', true);
INSERT INTO todo (title, done) VALUES ('Passear com o cachorro', false);
INSERT INTO todo (title, done) VALUES ('Ler um livro', true);
INSERT INTO todo (title, done) VALUES ('Fazer compras', false);
INSERT INTO todo (title, done) VALUES ('Ligar para o médico', false);
INSERT INTO todo (title, done) VALUES ('Limpar a casa', false);
INSERT INTO todo (title, done) VALUES ('Preparar o jantar', false);
INSERT INTO todo (title, done) VALUES ('Estudar para o teste', false);
INSERT INTO todo (title, done) VALUES ('Responder aos e-mails', true);
INSERT INTO todo (title, done) VALUES ('Assistir a um filme', false);
INSERT INTO todo (title, done) VALUES ('Consertar a torneira', false);
INSERT INTO todo (title, done) VALUES ('Enviar convites para a festa', false);
INSERT INTO todo (title, done) VALUES ('Planejar as férias', false);
INSERT INTO todo (title, done) VALUES ('Caminhar no parque', false);
INSERT INTO todo (title, done) VALUES ('Fazer exercícios de manhã', true);
INSERT INTO todo (title, done) VALUES ('Comprar flores', false);
INSERT INTO todo (title, done) VALUES ('Marcar reunião de equipe', false);
INSERT INTO todo (title, done) VALUES ('Escrever relatório', false);
INSERT INTO todo (title, done) VALUES ('Aprender a tocar violão', false);
INSERT INTO todo (title, done) VALUES ('Visitar o museu', false);
INSERT INTO todo (title, done) VALUES ('Fazer uma caminhada', true);
INSERT INTO todo (title, done) VALUES ('Ler notícias', true);
INSERT INTO todo (title, done) VALUES ('Praticar meditação', false);
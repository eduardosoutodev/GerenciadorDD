-- =========================================================
-- Banco de dados: GerenciadorDD3.2
-- Nome do banco ajustado para bater com util/Conexao.java
-- (a versão original do projeto criava "gerenciador_dd",
--  mas o código Java conecta em "gerenciador_dd3_2")
-- =========================================================

DROP DATABASE IF EXISTS gerenciador_dd3_2;
CREATE DATABASE gerenciador_dd3_2;
USE gerenciador_dd3_2;

-- Tabela de Campanhas
CREATE TABLE campanhas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    mestre VARCHAR(100)
);

-- Tabela de Personagens
CREATE TABLE personagens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    classe VARCHAR(50),
    nivel INT DEFAULT 1,
    raca VARCHAR(50),
    alinhamento VARCHAR(50),
    forca INT DEFAULT 10,
    destreza INT DEFAULT 10,
    constituicao INT DEFAULT 10,
    inteligencia INT DEFAULT 10,
    sabedoria INT DEFAULT 10,
    carisma INT DEFAULT 10,
    pv_max INT DEFAULT 10,
    pv_atual INT DEFAULT 10,
    ca INT DEFAULT 10,
    notas TEXT,
    campanha_id INT,
    FOREIGN KEY (campanha_id) REFERENCES campanhas(id) ON DELETE SET NULL
);

-- Tabela de Criaturas (Bestiário)
CREATE TABLE criaturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    nd VARCHAR(10),
    tamanho VARCHAR(20),
    ca INT,
    pv VARCHAR(50),
    forca INT,
    destreza INT,
    constituicao INT,
    inteligencia INT,
    sabedoria INT,
    carisma INT,
    habilidades TEXT,
    acoes TEXT
);

-- Tabela de Itens (Inventário)
CREATE TABLE itens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    quantidade INT DEFAULT 1,
    peso DECIMAL(5,2),
    personagem_id INT,
    FOREIGN KEY (personagem_id) REFERENCES personagens(id) ON DELETE CASCADE
);

-- =========================================================
-- Dados iniciais (mesmos do database.sql original do projeto)
-- =========================================================

INSERT INTO campanhas (nome, descricao, mestre) VALUES
('A Sombra do Dragão', 'Uma aventura épica contra um dragão ancestral.', 'Mestre Manus'),
('Segredos do Templo', 'Exploração de ruínas antigas.', 'Mestre RPG');

INSERT INTO personagens (nome, classe, nivel, raca, alinhamento, forca, destreza, constituicao, inteligencia, sabedoria, carisma, pv_max, pv_atual, ca, notas, campanha_id)
VALUES ('Aragorn', 'Patrulheiro', 5, 'Humano', 'Neutro e Bom', 16, 14, 15, 12, 13, 11, 50, 45, 16, 'Guardião do Norte.', 1);

INSERT INTO itens (nome, quantidade, peso, personagem_id) VALUES
('Espada Longa', 1, 1.5, 1),
('Escudo', 1, 3.0, 1),
('Armadura de Couro', 1, 8.0, 1);

INSERT INTO criaturas (nome, tipo, nd, tamanho, ca, pv, forca, destreza, constituicao, inteligencia, sabedoria, carisma, habilidades, acoes)
VALUES
('Goblin', 'Besta', '1/8', 'Pequeno', 15, '7 (2d6)', 8, 14, 10, 10, 8, 8, 'Fuga Ágil', 'Cimitarra, Arco Curto'),
('Orc', 'Humanoide', '1/2', 'Médio', 13, '15 (2d8 + 6)', 16, 12, 16, 7, 11, 10, 'Agressivo', 'Machado Grande, Azagaia'),
('Lich', 'Morto-vivo', '21', 'Médio', 17, '135 (18d8 + 54)', 11, 16, 16, 20, 14, 16, 'Resistência Lendária, Conjuração', 'Toque Paralisante, Raio da Morte');

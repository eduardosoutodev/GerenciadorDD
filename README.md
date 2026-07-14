# GerenciadorDD

Aplicação desktop em Java (Swing + MySQL) para gerenciar campanhas, personagens, criaturas e itens de mesas de D&D 5e.

Projeto Integrador — Desenvolvimento e Testes de Software (Senac). Este repositório contém a versão **refatorada** do sistema, reorganizada em camadas seguindo os princípios SOLID, como preparação para uma futura reescrita como aplicação web.

## Arquitetura

O projeto é dividido em quatro camadas com responsabilidades bem definidas:

```
src/
├── model/        # Entidades de domínio (Campanha, Personagem, Criatura, Item)
├── repository/   # Contratos de acesso a dados + implementações JDBC (repository/jdbc)
├── service/      # Regras de negócio, validações e cálculos (sem Swing, sem SQL)
├── view/         # Telas Swing (JFrame/JPanel), sem lógica de negócio
└── util/         # Conexão com o banco (Conexao) e leitura de configuração (DBConfig)
```

- **model** — entidades puras, sem dependência de banco ou interface gráfica.
- **repository** — interfaces por entidade (`CampanhaRepository`, `PersonagemRepository`, `CriaturaRepository`, `ItemRepository`) e suas implementações JDBC, isoladas em `repository/jdbc`.
- **service** — regras de negócio e validações (ex.: `AtributoService` calcula modificadores e bônus de proficiência de D&D 5e). Depende apenas das interfaces de `repository`, nunca das implementações concretas.
- **view** — telas responsáveis só por montar componentes e repassar ações do usuário para a camada de serviço.

A composição entre serviços e repositórios é centralizada na classe `service.ServiceFactory` (Factory Pattern), então trocar a fonte de dados no futuro (ex. de MySQL local para uma API web) exige mudança em um único lugar.

## Princípios SOLID aplicados

| Princípio | Onde |
|---|---|
| **SRP** | Cada `XxxService` cuida só das regras da própria entidade; cada `XxxRepositoryJDBC` cuida só de mapear `ResultSet` ↔ objeto; `Conexao` só abre conexão, `DBConfig` só lê configuração. |
| **OCP** | Novas formas de persistência podem ser adicionadas implementando as interfaces de `repository`, sem alterar os serviços existentes. |
| **LSP** | Qualquer implementação de uma interface `XxxRepository` pode substituir `XxxRepositoryJDBC` sem quebrar o comportamento esperado. |
| **ISP** | Interfaces de repositório separadas por entidade, em vez de uma interface genérica única. |
| **DIP** | Os serviços dependem das interfaces de `repository` (abstrações), não das implementações JDBC concretas — a implementação é injetada via construtor pela `ServiceFactory`. |

## Pré-requisitos

- JDK 21
- MySQL 8.0
- NetBeans (o projeto já vem com os arquivos de projeto do NetBeans em `nbproject/`)

## Configuração do banco de dados

1. Crie o banco executando o script `database.sql` (via MySQL Workbench, `mysql` no terminal, ou "Run SQL Script" no NetBeans).
2. Copie o arquivo de configuração de exemplo e ajuste com suas credenciais locais:

   ```bash
   cp src/db.properties.example src/db.properties
   ```

   Edite `src/db.properties` com a URL, usuário e senha do seu MySQL local. Esse arquivo não é versionado (está no `.gitignore`), justamente para não expor credenciais no repositório.

## Executando

Abra o projeto no NetBeans e rode a classe principal (`view.MainFrame`) normalmente (F6).

### Testes manuais via console

A classe `TesteMain` executa uma bateria de verificações simples no console, sem depender da interface gráfica:

- Cálculo de modificadores de atributo e bônus de proficiência (`AtributoService`).
- Rejeição de nome vazio ao criar personagem e campanha.
- Rejeição de atributo fora da faixa válida do D&D 5e (1 a 30).
- Rejeição de texto não numérico em campos numéricos.
- Um teste opcional de integração real com o MySQL, que avisa no console caso o banco não esteja acessível, em vez de derrubar a aplicação.

Para executar: no NetBeans, botão direito sobre `TesteMain.java` → **Run File** (ou Shift+F6).

## Sobre este projeto

Este repositório documenta a etapa de refatoração de um sistema desktop já funcional, sem alterar nenhum comportamento visível ao usuário final. O objetivo foi separar regras de negócio da interface gráfica para permitir que `model`, `repository` e `service` sejam reaproveitados em uma futura versão web do sistema, reescrevendo apenas a camada `view`.

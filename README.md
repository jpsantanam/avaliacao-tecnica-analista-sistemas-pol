# ⚖️ API de Gerenciamento de Processos Judiciais

Este projeto é uma API RESTful desenvolvida em **Java com Spring Boot** para gerenciar **processos judiciais** e seus respectivos **réus**. É parte de uma avaliação técnica para a vaga de Analista de Sistemas.

---

## 🚀 Tecnologias

-   Java 17
-   Spring Boot
-   Spring Data JPA
-   JUnit 5 + Mockito
-   Postman

---

## 📌 Funcionalidades

-   Criar, listar, atualizar e excluir **processos**
-   Adicionar **réus** a um processo
-   Evitar número de processo duplicado (`numero` único)
-   Testes automatizados com JUnit
-   Postman Collection incluída

---

## 💠 Como rodar o projeto

### Requisitos

-   Java 17+
-   Maven

### Passos

```bash
git clone https://github.com/jpsantanam/avaliacao-tecnica-analista-sistemas-pol.git
cd code
mvn spring-boot:run
```

Acesse em: [http://localhost:8080](http://localhost:8080)

---

## 📃️ Banco de Dados

### Gerado automaticamente

O Spring Boot está configurado para criar o banco com base nas entidades:

```properties
spring.jpa.hibernate.ddl-auto=update
```

### Configuração de Conexão com o Banco de Dados

A aplicação está configurada para se conectar a um banco de dados PostgreSQL. Certifique-se de que o banco esteja rodando e as credenciais estejam corretas. Abaixo está o arquivo `application.properties` com as configurações:

```properties
# Configuração do datasource PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/banco
spring.datasource.username=postgres
spring.datasource.password=postgres

# Configuração do JPA
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Caso necessário, ajuste os valores de `spring.datasource.url`, `spring.datasource.username` e `spring.datasource.password` para corresponder ao seu ambiente.

## 🔄 Endpoints principais

| Verbo HTTP | Rota                   | Ação                            |
| ---------- | ---------------------- | ------------------------------- |
| POST       | `/processos`           | Criar um novo processo          |
| GET        | `/processos`           | Listar todos os processos       |
| GET        | `/processos/{id}`      | Obter processo por ID           |
| PUT        | `/processos/{id}`      | Atualizar um processo existente |
| DELETE     | `/processos/{id}`      | Deletar um processo             |
| POST       | `/processos/{id}/reus` | Adicionar réu ao processo       |

---

## 🧲 Testes

Execute os testes automatizados com:

```bash
mvn test
```

---

## 📢 Postman

A coleção Postman com todos os endpoints está disponível em:

```
postman/API.postman_collection.json
```

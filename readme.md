# Desafio Técnico - Gerenciador de Usuários e Perfil

Este repositório contém a implementação de uma aplicação fullstack dividida em dois projetos:
- **`gerenciador-usuarios-api`**: Back-end em Java com Spring Boot
- **`gerenciador-usuarios-web`**: Front-end em Angular

## 📅 Objetivo
Desenvolver uma API RESTful para gerenciar usuários e perfis e uma interface web para consumo dessa API.

## ⚙️ Tecnologias Utilizadas

### Back-end (`gerenciador-usuarios-api`)
- Java 17
- Spring Boot 3.5.0
- Spring Data JPA + Hibernate
- PostgreSQL
- Flyway (versionamento de banco de dados)
- MapStruct (DTO mapping)
- SpringDoc OpenAPI (Swagger)
- JUnit, Mockito (testes)
- Docker

### Front-end (`gerenciador-usuarios-web`)
- Angular 19
- Angular Material
- RxJS
- TypeScript 5
- Docker

### Infraestrutura
- Docker Compose


## 🚀 Como Rodar o Projeto

### 1. Clone o repositório:
```bash
git clone https://github.com/rafaelluciodeveloper/gerenciador-usuarios
cd gerenciador-usuarios
```

### 2. Suba todos os serviços com Docker Compose:
```bash
docker-compose up --build -d
```

Esse comando irá subir:
- Banco de dados PostgreSQL
- API Spring Boot (`gerenciador-usuarios-api`)
- Aplicativo Angular (`gerenciador-usuarios-web`)

## 🌐 Acessos da Aplicação

### ✈️ Front-end Angular:
- http://localhost:4200

### ⚖️ Swagger da API:
- http://localhost:8080/swagger-ui/index.html


## 💡 Funcionalidades
- CRUD perfis de sistema
- CRUD usuários


## 🌍 Estrutura de Pastas

```
gerenciador-usuarios-api
├── config
├── controller
├── dto
├── exception
├── mapper
├── model
├── repository
└── service

gerenciador-usuarios-web
├── src
│   └── app
│       ├── components
│       ├── services
│       └── models
│       └── pages        
```

## 📊 Testes
- Os testes unitários estão localizados no módulo `gerenciador-usuarios-api`
- Utilizam `JUnit 5`, `Mockito` e `Spring Boot Test`

```bash
cd gerenciador-usuarios/gerenciador-usuarios-api
mvn test
```

## 💼 Critérios de Avaliação Atendidos
- [x] Estrutura limpa e bem organizada (Clean Architecture)
- [x] API REST com Spring Boot 3
- [x] Banco de dados com Flyway + PostgreSQL
- [x] Front-end Angular
- [x] Documentação via Swagger
- [x] Testes unitários com JUnit
- [x] Docker Compose com todos os serviços

---

## 🚪 Autor
**Rafael Lucio**  
LinkedIn: [https://www.linkedin.com/in/rafael-lucio-5b72a5103](https://www.linkedin.com/in/rafael-lucio-5b72a5103)  
Email: rafaellucio.developer@gmail.com


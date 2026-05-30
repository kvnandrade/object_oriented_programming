# Sistema Automotivo - Gestao de Estoque de Veiculos

API REST desenvolvida em Java 21 com Spring Boot para gerenciar o estoque de veiculos de uma concessionaria. O projeto permite cadastrar, listar, buscar, atualizar, excluir e filtrar veiculos por marca, modelo, ano, faixa de preco e status.

## Objetivo do projeto

O objetivo e criar um sistema simples, organizado e orientado a objetos para apoiar concessionarias e vendedores no controle de estoque de veiculos. A aplicacao centraliza informacoes como modelo, marca, ano, cor, preco, quilometragem e status de disponibilidade, facilitando consultas e atualizacoes.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- MySQL
- Maven
- H2 Database para teste automatizado simples

## Funcionalidades

- Cadastrar veiculo
- Listar todos os veiculos
- Buscar veiculo por ID
- Atualizar dados de um veiculo
- Excluir veiculo
- Cadastrar, listar, buscar, atualizar e excluir marcas
- Cadastrar, listar, buscar, atualizar e excluir modelos
- Associar modelos as suas marcas
- Registrar automaticamente marca/modelo quando um veiculo e cadastrado
- Filtrar por:
  - marca
  - modelo
  - ano
  - preco minimo
  - preco maximo
  - status: `DISPONIVEL`, `VENDIDO`, `RESERVADO`
- Validacao dos dados enviados
- Tratamento padronizado de erros
- Criacao automatica das tabelas pelo JPA/Hibernate

## Estrutura do projeto

```text
src
|-- main
|   |-- java
|   |   `-- br/com/estoque/veiculos
|   |       |-- config
|   |       |-- controller
|   |       |-- dto
|   |       |-- entity
|   |       |-- exception
|   |       |-- repository
|   |       |-- service
|   |       `-- EstoqueVeiculosApplication.java
|   `-- resources
|       `-- application.properties
`-- test
    |-- java
    `-- resources
```

### O que cada pasta faz

- `config`: guarda configuracoes da aplicacao. Neste projeto existe a configuracao de CORS.
- `controller`: recebe as requisicoes HTTP e devolve as respostas da API.
- `dto`: contem os objetos usados para entrada e saida de dados da API.
- `entity`: contem as classes que representam as tabelas do banco de dados.
- `exception`: centraliza as classes de erro e o tratamento global de excecoes.
- `repository`: faz a comunicacao com o banco usando Spring Data JPA.
- `service`: contem as regras de negocio e organiza o fluxo entre controller e repository.
- `resources`: guarda arquivos de configuracao, como `application.properties`.
- `test`: contem configuracoes e testes automatizados.

## O que cada classe faz

- `EstoqueVeiculosApplication`: classe principal. Inicia a aplicacao Spring Boot.
- `CorsConfig`: libera chamadas de frontends locais, como React em `localhost:3000` ou Vite em `localhost:5173`.
- `VeiculoController`: define os endpoints `/veiculos` e chama a camada de servico.
- `MarcaController`: define os endpoints `/marcas` para cadastro e consulta de marcas.
- `ModeloController`: define os endpoints `/modelos` para cadastro e consulta de modelos.
- `VeiculoRequestDTO`: representa os dados recebidos no cadastro e atualizacao. Tambem contem as validacoes.
- `VeiculoResponseDTO`: representa os dados devolvidos pela API.
- `MarcaRequestDTO` e `MarcaResponseDTO`: representam entrada e saida de dados de marcas.
- `ModeloRequestDTO` e `ModeloResponseDTO`: representam entrada e saida de dados de modelos.
- `Veiculo`: entidade JPA que representa a tabela `veiculos`.
- `Marca`: entidade JPA que representa a tabela `marcas`.
- `Modelo`: entidade JPA que representa a tabela `modelos` e se relaciona com uma marca.
- `StatusVeiculo`: enum com os status permitidos: `DISPONIVEL`, `VENDIDO`, `RESERVADO`.
- `VeiculoRepository`: interface que herda recursos prontos do Spring Data JPA, como salvar, buscar, listar e excluir.
- `MarcaRepository`: interface de acesso aos dados de marcas.
- `ModeloRepository`: interface de acesso aos dados de modelos.
- `VeiculoService`: concentra as regras de negocio do CRUD e dos filtros.
- `MarcaService`: concentra as regras de negocio de marcas.
- `ModeloService`: concentra as regras de negocio de modelos e associa cada modelo a uma marca.
- `RecursoNaoEncontradoException`: excecao usada quando um veiculo nao existe.
- `ErroResponse`: formato padronizado de resposta de erro.
- `GlobalExceptionHandler`: intercepta erros da aplicacao e transforma em respostas HTTP adequadas.
- `EstoqueVeiculosApplicationTests`: teste simples que verifica se o contexto Spring carrega corretamente.

## Como funciona o fluxo da aplicacao

1. O cliente envia uma requisicao HTTP pelo Postman, navegador ou frontend.
2. O `VeiculoController` recebe a requisicao.
3. Se houver corpo JSON, o Spring valida os dados usando as anotacoes do `VeiculoRequestDTO`.
4. O controller chama o `VeiculoService`.
5. O service executa a regra de negocio, como cadastrar, buscar, atualizar, excluir ou filtrar.
6. Ao cadastrar ou atualizar veiculo, o sistema garante que a marca e o modelo tambem existam nas tabelas proprias.
7. O repository acessa o banco MySQL usando Spring Data JPA.
8. O resultado volta para o service.
9. O service converte as entidades para DTOs de resposta.
10. O controller devolve a resposta HTTP com status adequado.
11. Se ocorrer erro, o `GlobalExceptionHandler` gera uma resposta padronizada.

## Como o Spring Boot esta sendo utilizado

- `@SpringBootApplication`: inicia a aplicacao e ativa a configuracao automatica.
- `@RestController`: cria endpoints REST.
- `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: mapeiam rotas HTTP.
- `@Service`: identifica a camada de regras de negocio.
- `@Repository` nao precisa ser escrito porque o Spring Data cria a implementacao automaticamente a partir de `JpaRepository`.
- `@Entity`, `@Id`, `@GeneratedValue`, `@Column`, `@Enumerated`: mapeiam a classe Java para tabela do banco.
- `@Transactional`: controla transacoes de banco nas operacoes de leitura e escrita.
- `@Valid`: ativa validacoes dos DTOs.
- `@RestControllerAdvice`: centraliza o tratamento de excecoes.

## Conceitos de Programacao Orientada a Objetos aplicados

- Classes: `Veiculo`, `VeiculoService`, `VeiculoController`, entre outras.
- Objetos: cada veiculo, marca e modelo cadastrados sao representados como objetos.
- Encapsulamento: os atributos da entidade sao privados e acessados por metodos.
- Metodos: a entidade possui comportamentos como `criar` e `atualizar`.
- Enum: `StatusVeiculo` restringe o status a valores validos.
- Associacao entre objetos: `Modelo` possui uma `Marca`, representando o relacionamento entre entidades.
- Separacao de responsabilidades: cada classe possui uma funcao bem definida.
- Abstracao: o repository esconde detalhes de SQL e fornece metodos de persistencia.
- Polimorfismo por interface: `VeiculoRepository` herda contratos do `JpaRepository` e `JpaSpecificationExecutor`.

## Requisitos para executar

- Java 21 instalado
- Maven instalado
- MySQL instalado e em execucao
- Postman ou Insomnia para testar a API

## Como instalar dependencias

No terminal, dentro da pasta do projeto:

```bash
mvn clean install
```

Esse comando baixa as dependencias do Maven, compila o projeto e executa os testes.

## Como configurar o MySQL

1. Acesse o MySQL pelo terminal ou pelo MySQL Workbench:

```bash
mysql -u root -p
```

2. Crie o banco de dados:

```sql
CREATE DATABASE estoque_veiculos;
```

3. Confira o arquivo `src/main/resources/application.properties`.

Ele ja esta configurado para conectar no MySQL local:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/estoque_veiculos?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=sua_senha_do_mysql
```

No projeto entregue, a senha deve ser a mesma senha usada na conexao `local` do MySQL Workbench.

O projeto usa:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Com isso, o Hibernate cria ou atualiza automaticamente a tabela `veiculos` quando a aplicacao inicia.

## Como executar a aplicacao

Execute:

```bash
mvn spring-boot:run
```

A aplicacao ficara disponivel em:

```text
http://localhost:8080
```

Para ver a tela do sistema, abra:

```text
http://localhost:8080/
```

Essa tela permite:

- cadastrar veiculos
- listar veiculos
- editar veiculos
- excluir veiculos
- filtrar veiculos
- cadastrar marcas
- cadastrar modelos

Os endpoints REST continuam disponiveis. Por exemplo, `/veiculos` mostra os dados em JSON:

```text
http://localhost:8080/veiculos
```

## Endpoints

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| POST | `/veiculos` | Cadastra um veiculo |
| GET | `/veiculos` | Lista todos os veiculos |
| GET | `/veiculos/{id}` | Busca veiculo por ID |
| PUT | `/veiculos/{id}` | Atualiza veiculo |
| DELETE | `/veiculos/{id}` | Exclui veiculo |
| GET | `/veiculos/filtro` | Filtra veiculos |
| POST | `/marcas` | Cadastra uma marca |
| GET | `/marcas` | Lista marcas |
| GET | `/marcas/{id}` | Busca marca por ID |
| PUT | `/marcas/{id}` | Atualiza marca |
| DELETE | `/marcas/{id}` | Exclui marca |
| POST | `/modelos` | Cadastra um modelo associado a uma marca |
| GET | `/modelos` | Lista modelos |
| GET | `/modelos?marcaId={id}` | Lista modelos de uma marca |
| GET | `/modelos/{id}` | Busca modelo por ID |
| PUT | `/modelos/{id}` | Atualiza modelo |
| DELETE | `/modelos/{id}` | Exclui modelo |

## Como testar no Postman

### 1. Cadastrar veiculo

Metodo:

```text
POST http://localhost:8080/veiculos
```

Headers:

```text
Content-Type: application/json
```

Body:

```json
{
  "modelo": "Civic",
  "marca": "Honda",
  "ano": 2022,
  "cor": "Prata",
  "preco": 135000.00,
  "quilometragem": 18000,
  "status": "DISPONIVEL"
}
```

Resposta esperada:

```json
{
  "id": 1,
  "modelo": "Civic",
  "marca": "Honda",
  "ano": 2022,
  "cor": "Prata",
  "preco": 135000.00,
  "quilometragem": 18000,
  "status": "DISPONIVEL"
}
```

Status HTTP: `201 Created`

### 2. Listar veiculos

```text
GET http://localhost:8080/veiculos
```

Resposta esperada:

```json
[
  {
    "id": 1,
    "modelo": "Civic",
    "marca": "Honda",
    "ano": 2022,
    "cor": "Prata",
    "preco": 135000.00,
    "quilometragem": 18000,
    "status": "DISPONIVEL"
  }
]
```

### 3. Buscar por ID

```text
GET http://localhost:8080/veiculos/1
```

Resposta esperada:

```json
{
  "id": 1,
  "modelo": "Civic",
  "marca": "Honda",
  "ano": 2022,
  "cor": "Prata",
  "preco": 135000.00,
  "quilometragem": 18000,
  "status": "DISPONIVEL"
}
```

### 4. Atualizar veiculo

```text
PUT http://localhost:8080/veiculos/1
```

Body:

```json
{
  "modelo": "Civic Touring",
  "marca": "Honda",
  "ano": 2022,
  "cor": "Prata",
  "preco": 132000.00,
  "quilometragem": 19500,
  "status": "RESERVADO"
}
```

Resposta esperada:

```json
{
  "id": 1,
  "modelo": "Civic Touring",
  "marca": "Honda",
  "ano": 2022,
  "cor": "Prata",
  "preco": 132000.00,
  "quilometragem": 19500,
  "status": "RESERVADO"
}
```

### 5. Excluir veiculo

```text
DELETE http://localhost:8080/veiculos/1
```

Resposta esperada: sem corpo.

Status HTTP: `204 No Content`

### 6. Filtrar veiculos

Exemplos:

```text
GET http://localhost:8080/veiculos/filtro?marca=Honda
```

```text
GET http://localhost:8080/veiculos/filtro?modelo=Civic&status=DISPONIVEL
```

```text
GET http://localhost:8080/veiculos/filtro?ano=2022&precoMin=100000&precoMax=150000
```

Resposta esperada:

```json
[
  {
    "id": 1,
    "modelo": "Civic",
    "marca": "Honda",
    "ano": 2022,
    "cor": "Prata",
    "preco": 135000.00,
    "quilometragem": 18000,
    "status": "DISPONIVEL"
  }
]
```

### 7. Cadastrar marca

```text
POST http://localhost:8080/marcas
```

Body:

```json
{
  "nome": "Honda"
}
```

Resposta esperada:

```json
{
  "id": 1,
  "nome": "Honda"
}
```

### 8. Cadastrar modelo

```text
POST http://localhost:8080/modelos
```

Body:

```json
{
  "nome": "Civic",
  "marca": "Honda"
}
```

Resposta esperada:

```json
{
  "id": 1,
  "nome": "Civic",
  "marcaId": 1,
  "marca": "Honda"
}
```

### 9. Listar modelos de uma marca

```text
GET http://localhost:8080/modelos?marcaId=1
```

Resposta esperada:

```json
[
  {
    "id": 1,
    "nome": "Civic",
    "marcaId": 1,
    "marca": "Honda"
  }
]
```

## Exemplos de erros

### Veiculo nao encontrado

```json
{
  "timestamp": "2026-05-30T12:00:00",
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Veiculo com id 99 nao encontrado",
  "caminho": "/veiculos/99",
  "detalhes": []
}
```

### Validacao

```json
{
  "timestamp": "2026-05-30T12:00:00",
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "Existem campos invalidos na requisicao",
  "caminho": "/veiculos",
  "detalhes": [
    "modelo: O modelo e obrigatorio",
    "preco: O preco deve ser maior que zero"
  ]
}
```

## Como subir para o GitHub

Caso voce esteja criando o repositorio localmente do zero:

```bash
git init
git add .
git commit -m "Cria sistema de gestao de estoque de veiculos"
git branch -M main
git remote add origin https://github.com/seu-usuario/seu-repositorio.git
git push -u origin main
```

Se o repositorio ja existir, normalmente basta:

```bash
git add .
git commit -m "Implementa API de estoque de veiculos"
git push
```

## O que ainda falta para a parte teorica

O codigo esta pronto para a parte pratica, mas a entrega do professor tambem pede um relatorio em PDF. Para concluir essa parte, ainda e necessario produzir:

1. Levantamento de requisitos com pelo menos 10 perguntas e respostas simuladas.
2. Descricao das entidades principais do sistema, como veiculo, marca e modelo.
3. Explicacao dos conceitos de POO usados: classes, atributos, metodos, encapsulamento, enum e separacao de responsabilidades.
4. Lista de requisitos funcionais e nao funcionais.
5. Justificativa das tecnologias escolhidas: Java, Spring Boot, MySQL e Maven.
6. Prints do sistema funcionando no Postman.
7. Prints do codigo principal.
8. Link do repositorio publico no GitHub.

## O que ainda falta para o video pitch

O video deve ter ate 4 minutos. Sugestao de roteiro:

1. Apresentar o problema: controle de estoque de veiculos em concessionarias.
2. Explicar o objetivo do sistema.
3. Mostrar rapidamente a estrutura do projeto.
4. Demonstrar o CRUD no Postman:
   - cadastrar
   - listar
   - buscar por ID
   - atualizar
   - excluir
5. Demonstrar filtros por marca, ano, preco e status.
6. Explicar que o banco e MySQL e que as tabelas sao criadas pelo JPA.
7. Finalizar informando que o codigo esta publicado no GitHub.

Depois, publique no YouTube como "nao listado" e envie o link na plataforma do professor.

## Checklist final

- [ ] Codigo pronto
- [ ] Banco configurado
- [ ] Aplicacao funcionando
- [ ] GitHub publicado
- [ ] Prints necessarios
- [ ] PDF do relatorio
- [ ] Video Pitch

## Revisao geral dos requisitos

| Requisito | Status |
| --- | --- |
| Java 21 | Atendido |
| Spring Boot | Atendido |
| Spring Data JPA | Atendido |
| MySQL | Atendido |
| Maven | Atendido |
| CRUD completo de veiculos | Atendido |
| Cadastro de marcas | Atendido |
| Cadastro de modelos | Atendido |
| Associacao entre marcas e modelos | Atendido |
| Campos obrigatorios do veiculo | Atendido |
| Status DISPONIVEL, VENDIDO, RESERVADO | Atendido |
| Filtro por marca | Atendido |
| Filtro por modelo | Atendido |
| Filtro por ano | Atendido |
| Filtro por faixa de preco | Atendido |
| Filtro por status | Atendido |
| Controller | Atendido |
| Service | Atendido |
| Repository | Atendido |
| Entity | Atendido |
| DTO | Atendido |
| Exception | Atendido |
| Config | Atendido |
| Validacoes | Atendido |
| Tratamento de erros | Atendido |
| Responses adequadas | Atendido |
| README profissional | Atendido |

## Possiveis melhorias futuras

- Adicionar paginacao na listagem.
- Adicionar ordenacao por preco, ano ou marca.
- Criar autenticacao para usuarios administradores.
- Criar frontend simples para demonstracao visual.
- Adicionar testes automatizados para controller e service.
- Gerar documentacao Swagger/OpenAPI.
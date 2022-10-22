Assistir podcast do spring e enisirer o que entendeu aqui

Spring veio para facilitar (deixar as configurações mais simples)

# Spring Boot API REST: construa uma API

## Introdução ao Spring Boot

✅ Spring é um dos Frameworks mais antigos do java

✅ A ideia do Spring Boot é que consigamos desenvolver uma aplicação sem uso de container

✅ Spring Boot é muito utilizado para montar microserviços

Para inicializar este projeto foi utilizado o spring-initializr que é um gerador de projetos com String Boot para gerar
o projeto que já vem com a
estrutura do Maven através do site https://start.spring.io/

### Anotações vistas:

**@Controller -** faz com que o Spring consiga encontrar a classe atráves dessa anotação e o Spring faz o gerenciamento
dela

**@RequestMapping("/") -** Para dizer em qual url o Spring vai chamar o método retornaLista(), como esta sendo
desenvolvido localmente é http://localhost:8080/topicos

**@ResponseBody -** Inserimos essa anotação pois não temos uma página em nosso projeto, porque se não o Spring vai
considerar que o retorno no caso a String "Hello World" é uma página e ele vai tentar procurar essa página em
nosso projeto

**@RestController -** Serve para indicar que todo o método vai ter a anotação @ResponseBody, assumindo que este é
comportamento padrão de todos os métodos

> Indicar ao Spring que o retorno do método deve ser devolvido como resposta ( Por padrão, o Spring considera que o
> retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o
> retorno do método deve ser serializado e devolvido no corpo da resposta.)

## ❗ Importante

- Tomar cuidado ao criar pacotes no projeto, pois o Spring Boot somente carrega classes que forem criadas no **mesmo
  pacote** ao qual está a classe main


- Não é uma boa prática devolver entidades da JPA no seu controller, porque na entidade da JPA - na classe de domínio -
  você tem um monte de atributos (título, mensagem, data de criação e outros). E você tem atributos que são outras
  entidades, outras classes que têm outros atributos, sendo que um deles pode ser outra classe. O Jackson, por padrão, "
  serializa" todos os atributos que estiverem dentro da classe. E nem sempre, eu vou querer devolver no meu JSON todos
  os
  atributos da minha classe. Posso querer devolver só dois, três atributos ou ter uma flexibilidade. Por exemplo, em um
  endpoint devolver x atributos, e, em outro, y.
  Se você sempre devolve a classe de domínio - a entidade da JPA - ele sempre vai devolver todos os atributos,
  você não tem flexibilidade. Por isso não é uma boa prática.

## Publicando Endpoints

Módulo Devtools - serve para não ter que ficar reiniciando o servidor a cada modificação no código da aplicação. Para
adicioná-lo devemos fazer isso via dependencia do maven

### API REST

> "REST" é uma abreviação para Representational State Transfer (Transferência do Estado Representacional), nada mais é
> do que um modelo de arquitetura para sistemas distribuídos.Toda a arquitetura que você tem em um sistema conversando
> com
> outro é um sistema distribuído.

No modelo REST as coisas que são gerenciadas são chamadas de recursos, seguindo o exemplo conforme o projeto forum os
recursos seriam: Aluno, Topico, Resposta, Curso.

#### URI

Como temos vários recursos que a aplaicação vai manipular, conseguimos diferenciarmos através do conceito <strong>
URI</strong>, ou seja, cada recurso vai ter um <strong>Identificador ùnico</strong>, Por exemplo: do aluno, poderia
ser "/alunos"; tópicos, "/topicos"; resposta, "/respostas"; curso, "/cursos". Essa URI é o identificador único de cada
recurso. Com isso, consigo diferenciar qual recurso que a API vai manipular.

#### Verbos HTTP (métodos HTTP)

> Utilizamos métodos HTTP para manipular ações que podem ser realizadas nos recursos, pelos verbos HTTP conseguimos
> diferenciar a manipulação que quero fazer em cima do recurso

* "GET" para fazer leitura. Se eu disparar uma requisição "GET" para o recurso "/alunos", é porque quero recuperar os
  recursos;
* Se eu disparar um "POST", quero cadastrar;
* O "PUT", atualizar e o
* "DELETE", excluir.

Só que, como essa manipulação vai funcionar?
Como o cliente me manda uma representação desse recurso? E como a API REST devolve essa representação?

É aí que entra o "R" do REST (Representational).
A API transfere uma representação do recurso, ela recebe e devolve representações de determinados recursos.
E essas representações são feitas pelos "Media types", pelos formatos.
Geralmente o mais utilizado é o JSON, mas poderia ser qualquer formato, como XML, HTML, TXT, binário ou qualquer outro.

> Com isso, conseguimos representar esses meus recursos. Daí que veio a ideia do nome REST, porque o que o servidor faz
> é transferir uma representação de um recurso - do estado atual daquele recurso.

## Usando Spring Data

- Por padrão o Spring Boot utiliza o Hibernate como implementação da JPA.

- JPA é uma coleção de classes e métodos voltados para armazenar persistentemente as vastas quantidades de dados em um
  banco de dados.

Ao utilizar JPA as classes de dominio que vão representar as tabelas no BD precisam ser transformadas em entidades
através da anotação **@Entity** e em cima do atributo que representa a chave primária, tem que ter duas anotações, o
**@Id** e o **@GeneratedValue** (no caso, a chave primária vai ser gerada automaticamente pelo banco de dados).

> Em uma aplicação Spring Boot, são declaradas as configurações do banco de dados utilizado por ela no arquivo
> application.properties

**@ManyToOne -** para indicar a cardinalidade de muitos para um em um atributo com relacionamento

> Em uma aplicação que utiliza o Spring Data JPA, o acesso ao banco de dados é feito com a criação de uma interface,
> seguindo o padrão Repository. Exemplo: public interface TopicoRepository
> extends JpaRepository<Topico, Long>{}

Em uma interface não precisa colocar nenhuma anotação em cima dela, pois o Spring já encontra a classe automaticamente.

> Spring Data tem um padrão de nomenclatura e se seguirmos esse padrão ele consegue gerar a query de consulta ao BD
> automaticamente, no nosso projeto, baseado no nome do método na interface repository.<br>
> Padrão de nomenclatura ao filtrar por um atributo de um relacionamento em uma entidade:<br><br>
> É válido separar o nome do atributo, que representa o relacionamento, do nome do atributo a ser filtrado, com o uso do
> operador - <strong>findByCurso_Nome(String nomeCurso);</strong><br><br>
> É possível declarar o nome do método concatenando nele o nome do atributo que representa o relacionamento, seguido do
> nome do atributo a ser filtrado - <strong>findByCursoNome(String nomeCurso);</strong>

## Trabalhando com POST

**@ResquestBody -**  Indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao
parâmetro do método

**@RequestMapping -** Quando utilizado em cima da classe, serve para evitar repetir a URL em todos os métodos da classe
controller.

**@PostMapping -** Serve para mapear requisições do tipo POST

✅ Devemos utilizar a classe **ResponseEntity** para montar uma resposta a ser devolvida para o cliente da API

### Boas práticas no cadastro - POST

✅ Boas práticas do REST é que ao fazer um POST devemos devolver o status code 201, que significa que algo foi criado,
com cabeçalho location, que o valor vai ser a URI e o
corpo da resposta sendo uma representação do recurso que acabou de ser criado;

✅ Para receber dados enviados no corpo da requisição, a boa prática é criar uma classe que também siga o padrão DTO (
Data Transfer Object), conforme classe TopicoForm.

### Postman

* Ao utilizar o verbo HTTP POST - Deve ser definido no Postman:
    * **JSON -** Como precisamos enviar o corpo da requisição, os parametros, as informações para fazer o cadastro,
      clicar em body
      -> raw e inserir o JSON<br><br>

    * **Cabeçalho -** Precisamos definir um cabeçalho para indicar para o servidor, que o corpo da requisição está em
      formaro JSON, em
      Headers -> Key = content-type e o value = application/json. **(O cabeçalho content-type é para o cliente dizer
      qual
      o tipo de conteúdo que esta sendo enviado).**

## Validação com Bean Validation

> Bean Validation é uma especificação do java que se integra com o Spring

Para fazer validações das informações enviadas pelos clientes da API, podemos utilizar a especificação Bean Validation,
com as anotações:

* **@NotNull -** Faz uma validação em que o atributo não pode ser nulo
* **@NotEmpty -** Faz uma validação em que o atributo não pode ser vazio

**As anotações do Bean Validation são inseridas nos atributos onde queremos que ocorra a validação.**

Mas além das anotações, precisamos dizer para o Spring chamar/executar o Bean Validation caso algum parâmetro esteja
inválido
conforme as anotações colocadas no atributo, fazemos isso com a anotação **@Valid** colocando ela onde vamos receber o
corpo da requisição, e com isso caso algum parâmetro esteja inválido vai ser retornado o status code 400.

### Simplificando o JSON 
> JSON retornado no corpo da resposta para o cliente de uma requisição inválida

interceptador é chamado de Controller Advice

inserir entendimento aqui 21/10/2022
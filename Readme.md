Assistir podcast do spring e enisirer o que entendeu aqui

Spring é um dos Frameworks mais antigos do java

A ideia do Spring Boot é que consigamos desenvolver uma aplicação sem uso de container

Spring Boot muito utilizado para montar microserviços

# Spring Boot API REST: construa uma API

## Introdução ao Spring Boot

https://start.spring.io/
Foi utilizado o spring-initializr que é um gerador de projetos com String Boot para gerar o projeto que já vem com a
estrutura do Maven

Anotações vistas:
@Controller - que faz com que o Spring consegue encontrar essa classe atráves dessa anotação e faz o gerenciamento dela
public class HelloController {

@RequestMapping("/") - Para dizer em qual url o Spring vai chamar o método Hello()

@ResponseBody - Inserimos essa anotação pois não temos uma página em nosso projeto, porque se não o Spring vai
considerar que o retorno no caso a String "Hello World" é uma página e ele vai tentar procurar essa página em
nosso projeto

@RestController - Serve para indicar que todo o método vai ter a anotação @ResponseBody, assumindo que este é
comportamento padrão de todos os métodos

> Indicar ao Spring que o retorno do método deve ser devolvido como resposta ( Por padrão, o Spring considera que o
> retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o
> retorno do método deve ser serializado e devolvido no corpo da resposta.)

!Important
Tomar cuidado ao criar pacotes no projeto, pois o Spring Boot somente carrega classes que forem criadas no **mesmo
pacote** ao qual está a classe main

Não é uma boa prática devolver entidades da JPA no seu controller, porque na entidade da JPA - na classe de domínio -
você tem um monte de atributos (título, mensagem, data de criação e outros). E você tem atributos que são outras
entidades, outras classes que têm outros atributos, sendo que um deles pode ser outra classe. O Jackson, por padrão, "
serializa" todos os atributos que estiverem dentro da classe. E nem sempre, eu vou querer devolver no meu JSON todos os
atributos da minha classe. Posso querer devolver só dois, três atributos ou ter uma flexibilidade. Por exemplo, em um
endpoint devolver x atributos, e, em outro, y.

[05:46] Se você sempre devolve a classe de domínio - a entidade da JPA - ele sempre vai devolver todos os atributos,
você não tem flexibilidade. Por isso não é uma boa prática.

## Publicando Endpoints

Módulo Devtools - serve para não ter que ficar reiniciando o servidor a cada modificação no código da aplicação. Para
adicioná-lo devemos fazeri isso via dependencia do maven

# Dúvidas: REVER NO FINAL DE SEMANA

* Como ao executar o main ForumAplication ele chama o topicosController, no código não ved=jo nada ligando eles

* Minha aplicação esta com o devtools ativo porém a cada modificaçõa no cóidgo o servidor não se atualiza, o que fazer?

* Não entendi pedir explicação passo a passo.
  return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());

* JSP, ou Thymeleaf o que é?
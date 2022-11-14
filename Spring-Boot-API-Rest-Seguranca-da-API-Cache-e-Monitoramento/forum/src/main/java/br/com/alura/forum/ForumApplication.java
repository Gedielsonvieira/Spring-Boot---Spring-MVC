package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching// habilita o uso de cache na aplicação
@EnableSpringDataWebSupport
@EnableSwagger2//Para habilitar o swagger
/*
Para receber os parâmetros de ordenação e paginação diretamente nos métodos do controller, devemos habilitar o módulo
SpringDataWebSupport, adicionando a anotação @EnableSpringDataWebSupport na classe ForumApplication(main)
*/
public class ForumApplication {//Classe que vamos utilizar para rodar nosso projeto

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}

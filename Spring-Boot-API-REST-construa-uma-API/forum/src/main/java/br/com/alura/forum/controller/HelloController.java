package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller//Spring consegue encontrar essa classe atráves dessa anotação e faz o gerenciamento dela
public class HelloController {

    @RequestMapping("/")//Para dizer em qual url o Spring vai chamar o método Hello()
    @ResponseBody/*Inserimos essa anotação pois não temos uma página em nosso projeto, porque se não o Spring vai
    considerar que o retorno no caso a String "Hello World" é uma página e ele vai tentar procurar essa página em
    nosso projeto*/
    public String Hello(){
        return "Hello World";
    }

}

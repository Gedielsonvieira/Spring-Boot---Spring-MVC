package br.com.alura.forum.controller;

import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//Cliente vai chamar o endpoint /auth com o método post, vai mandar um json com email, senha. Vamos Receber no parametro do método autenticar

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    // O método autenticar vai ser chamado pelo cliente quando ele solicitar os dados de login, então o usuário vai entrar
    // na tela do front-end dele digitar email e senha e ao clicar no botão logar a aplicação cliente vai chamar nossa API
    // Rest e vai passar como parâmetro o email e senha
    // OBS: a aplicação cliente tem que configurar para chamar o endereço /auth com a requisição post
    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {
        System.out.println(form.getEmail());
        System.out.println(form.getSenha());
        return ResponseEntity.ok().build();
    }
}

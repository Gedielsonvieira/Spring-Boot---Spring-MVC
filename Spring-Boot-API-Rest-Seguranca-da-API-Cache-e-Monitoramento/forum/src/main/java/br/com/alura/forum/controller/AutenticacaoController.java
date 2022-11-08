package br.com.alura.forum.controller;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//Cliente vai chamar o endpoint /auth com o método post, vai mandar um json com email, senha. Vamos Receber no parametro do método autenticar

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    //Para fazer uma autenticação de maneira manual no Spring Security precisamos dessa classe
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    // O método autenticar vai ser chamado pelo cliente quando ele solicitar os dados de login, então o usuário vai entrar
    // na tela do front-end dele digitar email e senha e ao clicar no botão logar a aplicação cliente vai chamar nossa API
    // Rest e vai passar como parâmetro o email e senha
    // OBS: a aplicação cliente tem que configurar para chamar o endereço /auth com a requisição post
    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);//Essa linha é confusa

            //Antes de devolver o ok, precisamos gerar o token
            //Para identificar quem é o usuário e para qual usuário pertence o token, o método gerarToken recebe como parâmetro o authentication pois com ele conseguimos extrair o usuário que esta logado no sistema
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}

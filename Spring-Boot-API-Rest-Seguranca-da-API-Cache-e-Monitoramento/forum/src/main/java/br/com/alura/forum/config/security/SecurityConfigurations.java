package br.com.alura.forum.config.security;


import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity// Habilita o modulo de segurança na aplicação
@Configuration/* Como essa classe irá ter configurações precisamos dessa anotação, que ao startar a aplicação o Spring irá carregar e
ler as configurações que estiverem dentro desta classe */
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AutenticacaoService autenticacaoService;

    /*    ------------Tentando intender Autowired:-------------- */
    private UsuarioRepository usuarioRepository;

    public SecurityConfigurations(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    @Autowired
    AutenticacaoService autenticacaoService = new AutenticacaoService(usuarioRepository);

    //Configurações de autenticação (controle de acesso - login)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //passamos por parametro do metodo userDetailsService qual é a classe/qual é a service que tem a lógica de autenticação
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());

    }

    //Configurações de autorização (url, perfil de acesso)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Liberando acesso aos endpoints públicos:
        http.authorizeRequests() // -> Quais requisições vamos autorizar e como vai ser esta autorização
                // Requisiçoes permitidas/públicas somente para o endpoint que lista todos os tópicos e o que detalha um tópico específico
                .antMatchers(HttpMethod.GET, "/topicos").permitAll() // método GET de /topicos esta permitido (Liberando o acesso a url /topicos somente do verbo GET)
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                .anyRequest().authenticated()// Qualquer outra requisição tem que estar autenticada
                .and().formLogin();
    }

    //Configurações de recursos estáticos (requisições para aqureuivos - JS,CSS, images)
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    /*public static void main(String[] args) {
        //Para saber a hash no formato do BCrypt da senha 123456
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }*/
}

package br.com.alura.forum.config.security;


import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity// Habilita o modulo de segurança na aplicação
@Configuration/* Como essa classe irá ter configurações precisamos dessa anotação, que ao startar a aplicação o Spring irá carregar e
ler as configurações que estiverem dentro desta classe */
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Autowired //parece algo fixo n consigo enxergar como esse token vai variar levando em consideração cada cliente
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private AutenticacaoService autenticacaoService;

    @Autowired
    public SecurityConfigurations(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

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
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .anyRequest().authenticated()// Qualquer outra requisição tem que estar autenticada
                .and().csrf().disable()//csrf é uma abreviação para um tipo de ataque hacker que acontece em aplicação web e como vamos fazer autenticação via token a nossa API está livre desse ataque
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Aqui avisamos para o Spring que ao ser realizado alguma autenticação não é para criar seção
                .and().addFilterBefore(new AutenticacoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);//Para registrar a classe que tem o filtro não é via anotação tem de ser na classe SecurityConfiguration e adicionamos o filtro antes do filtro de autenticação padrão do Spring
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

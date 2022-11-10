package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

//Essa classe foi criada porque essa lógica de pegar o token via cabeçalho tem que acontecer antes de cair no controller
//Para criar um filtro no Spring, devemos criar uma classe que herda da classe OncePerRequestFilter
public class AutenticacoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public AutenticacoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //A nossa autenticação é STATELESS agora não existe mais o conceito de usuário logado, então quando o usuário disparou a requisição para se autenticar com email e senha
        //foi gerado um token e foi devolvido para ele, ....

        String token = recuperarToken(request);
        boolean tokenValido = tokenService.isTokenValido(token);
        if (tokenValido) {
            autenticarCliente(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            //não entendi como caiu nesse laço ao não passar o token, pois tem a palavra Bearer não sendo null nem vazio e começa com Bearer
            return null;
        }
        return token.substring(7, token.length());
    }
}

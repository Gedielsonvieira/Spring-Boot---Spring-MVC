package br.com.alura.forum.config.security;

import br.com.alura.forum.controller.TopicosController;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Para informarmos para o Spring Que essa é a service que tem a lógica de autenticação devemos implementar a interface UserDetailsService
@Service
public class AutenticacaoService implements UserDetailsService {
//    @Autowired
//    private UsuarioRepository usuarioRepository;


    /*  ------------Tentando intender Autowired:-------------- */

    private UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    // Ao preencher email e senha e clicar no btn de login o Spring que essa é a classe de autenticação e vai chamar o
    // método loadUserByUsername e esse método recebe como parâmetro o e-mail, e nós fizemos a consulta ao BD somente com o e-amil e a senha ele faz isso em memória
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByEmail(username);
        if (user.isPresent()) {
            return user.get();//user aqui é o Optional por isso pegamos o usuario com o get
        } else {
            throw new UsernameNotFoundException("Dados inválidos!");
        }
    }
}

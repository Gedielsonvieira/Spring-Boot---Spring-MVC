package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/topicos")
// Como este Controller responde as requisisçõa que começam com /topicos e para não ter que ficar repetindo essa anotação em cada método foi inserido na classe.
public class TopicosController {

    @Autowired // injeção de dependência do topicoRepository
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
// utilizando o verbo HTTP GET para diferenciar os métodos, pois os dois fazem parte do mesmo recurso /topicos
    // Dto dados que saem da API e vão para o cliente
    public List<TopicoDto> retornaLista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

    // Para fazer o post - cadastro de tópicos, precisaremos de um novo método
    // Classe TopicoForm recebe as informações (objeto topico), e ela foi criada para não devolver a entidade no controller,
    // TopicoForm é um Dto porém porém são dados que chegam do cliente para a API
    // @RequestBody - indica para o Spring que os dados (topicoForm) devem ser pegos do corpo da requisição
    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        //topicoForm o cliente mando um JSON e o Jackson converte isso em um objeto do tipo TopicoForm

        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    // Como temos dois verbos Get se não especificarmos qual topico queremos detalhar terá conflito
    // @PathVariable precisamos avisar para o Spring que o parâmetro Long id não virá numa interrogação e sim no barra ("/"), na URL.
    // Para dizer isso pro Spring, existe uma anotação, @PathVariable, que se trata de uma variável do Path, da URL.
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {//path dinâmico
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
            //precisamos dar um get porque até então o tópico, é um tópico do tipo Optional e quando damos um get, de fato ele acessa um topico do Tipo Topico
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm atualizacaoTopicoForm) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            //Não conseguimos ter essas informações dentro da classe atualizacaoTopicoForm por isso estamos passando via parâmetro
            Topico topico = atualizacaoTopicoForm.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}

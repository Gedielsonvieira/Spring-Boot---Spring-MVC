package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// O 1° parâmetro do gerenirc da JpaRepository é a entidade que esse repository vai trabalhar,
// e o segundo é o tipo do atributo do id, no nosso caso é Long, conforme a classe Topico
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
}

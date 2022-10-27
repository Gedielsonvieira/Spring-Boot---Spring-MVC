package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Topico;

//A classe TopicoDto foi criada para devolver somente os dados que queremos devolver no endpoit /topicos, porque não é uma boa prática devolver uma entidade JPA no controller, pois pode haver dados sensíveis.
public class TopicoDto {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return dataCriacao;
    }

    public static List<TopicoDto> converter(List<Topico> topicos) {
        return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
        // Essa é a sintaxe do Java 8.
        // Sem ele, teríamos que pegar essa lista de tópicos, fazer um for para cada tópico, dar new no topicoDto,
        // guardar em uma lista de topicoDto e devolver essa lista de topicoDto no final.
        // Desse jeito, ele faz tudo isso em uma linha só usando API de strings do Java 8.
    }
}

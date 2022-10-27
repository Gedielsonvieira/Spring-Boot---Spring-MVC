package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//classe criada porque ao atualizar não podemos atualizar todas as informações do TopicoForm
//Aluno só poderá atualizar titulo e mensagem
public class AtualizacaoTopicoForm {
    @NotNull @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.setTitulo(this.titulo);//this nesse caso é o json que foi enviado do postman para a API (que o usuário enviou)
        topico.setMensagem(this.mensagem);
        return topico;
    }
}

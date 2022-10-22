package br.com.alura.forum.controller.form;

//Esse DTO representa o erro de algum campo
public class ErroDeFormularioDto {
    private String campo;
    private String mensagem;

    public ErroDeFormularioDto(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getcampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}

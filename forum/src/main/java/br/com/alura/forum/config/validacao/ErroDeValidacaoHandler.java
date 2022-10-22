package br.com.alura.forum.config.validacao;

import br.com.alura.forum.controller.form.ErroDeFormularioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice // Anotação que indica que esta classe é um controller advice - que serve como um interceptador
// Se e usar aqui o ControllerAdvice o que acontece? ver depois
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Indica para o Spring que esse método deve ser chamado quando ter alguma exceção dentro de algum controller e
    // deve ser passada como parametro o tipo de exceção que quando acontecer no controller executar o método
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ArrayList<ErroDeFormularioDto> dto = new ArrayList<>();

        fieldErrors.forEach(error -> {
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erroDeFormularioDto = new ErroDeFormularioDto(error.getField(), mensagem);
            dto.add(erroDeFormularioDto);
        });

        return dto;
    }
}

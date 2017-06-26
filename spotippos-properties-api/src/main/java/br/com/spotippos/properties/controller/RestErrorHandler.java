package br.com.spotippos.properties.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe para tratar os erros de validação da API
 *
 * @author Vinicius Cunha
 */
@ControllerAdvice
public class RestErrorHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VndErrors processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<VndErrors.VndError> fieldErrors = result.getFieldErrors().stream().map(error ->
                new VndErrors.VndError(error.getField(), error.getDefaultMessage())
        ).collect(Collectors.toList());

        return new VndErrors(fieldErrors);
    }

}

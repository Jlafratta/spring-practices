package com.utn.springpractices.controller;

import com.utn.springpractices.exception.notFound.PersonNotFoundException;
import com.utn.springpractices.exception.notFound.PetNotFoundException;
import com.utn.springpractices.model.DTO.ErrorResponseDto;
import com.utn.springpractices.utils.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 *  Filtro que maneja las excepciones que se lanzan en los rest controller,
 *  les asigna un http status y devuelve un mensaje de error (opcional)
 **/

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotFoundException.class)
    public ErrorResponseDto handlePersonNotFoundException(PersonNotFoundException e) {
        return ErrorResponseDto.builder()
                .code(404)
                .description(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PetNotFoundException.class)
    public ErrorResponseDto handlePetNotFoundException(PetNotFoundException e) {
        return ErrorResponseDto.builder()
                .code(404)
                .description(e.getMessage())
                .build();
    }
}

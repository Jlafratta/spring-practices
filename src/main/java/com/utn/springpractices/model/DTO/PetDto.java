package com.utn.springpractices.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.springpractices.model.Person;
import com.utn.springpractices.utils.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.io.Serializable;

@Data
public class PetDto implements Serializable {

    @NotNull(message = ErrorMessage.NOT_NULL)
    private String name;

    @NotNull(message = ErrorMessage.NOT_NULL)
    @Positive
    private Integer weight;

    @JsonIgnore
    private Person person;

}
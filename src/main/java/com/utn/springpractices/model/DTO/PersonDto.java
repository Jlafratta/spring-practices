package com.utn.springpractices.model.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.utn.springpractices.model.Pet;
import com.utn.springpractices.utils.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonDto implements Serializable {

    @NotNull(message = ErrorMessage.NOT_NULL)
    private String firstname;

    @NotNull(message = ErrorMessage.NOT_NULL)
    @Size(min=2, message = "Last name must not be less than 2 characters")
    private String lastname;

    private List<Pet> pets;

}

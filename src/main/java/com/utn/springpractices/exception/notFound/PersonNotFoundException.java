package com.utn.springpractices.exception.notFound;

import com.utn.springpractices.utils.ErrorMessage;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(){
        super(ErrorMessage.PERSON_NOT_FOUND);
    }
}

package com.utn.springpractices.exception.notFound;

import com.utn.springpractices.utils.ErrorMessage;

public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException(){
        super(ErrorMessage.PET_NOT_FOUND);
    }
}

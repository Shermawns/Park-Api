package com.Park_Api.exceptions.errors;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String string){
        super(string);
    }
}

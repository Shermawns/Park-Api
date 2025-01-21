package com.Park_Api.exceptions.errors;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String string){
        super(string);
    }
}

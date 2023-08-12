package com.viniciusrezende.todosimple.services.exceptions;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataBindingViolationExcetion  extends DataIntegrityViolationException{
    public DataBindingViolationExcetion(String msg) {
        super(msg);
    }
}

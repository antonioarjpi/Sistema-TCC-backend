package com.estacio.tcc.service.exceptions;

public class AuthenticateErrorException extends RuntimeException {
    public AuthenticateErrorException(String msg) {
        super(msg);
    }
}

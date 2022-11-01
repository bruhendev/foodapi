package com.spring.foodapi.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}

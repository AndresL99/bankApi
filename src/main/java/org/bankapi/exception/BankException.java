package org.bankapi.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankException extends Exception{

    private static final long serialVersionUID = 1L;

    private String message;

    public BankException(String message) {
        setMessage(message);
    }
}

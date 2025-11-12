package org.bankapi.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handledConstraintViolation(ConstraintViolationException exception, WebRequest webRequest)
    {
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation constraintViolation: exception.getConstraintViolations())
        {
            errors.add(constraintViolation.getRootBeanClass().getName()+""+constraintViolation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,exception.getLocalizedMessage(),errors);
        return new ResponseEntity<Object>(apiError,new HttpHeaders(),apiError.getHttpStatus());
    }

    @ExceptionHandler({BankException.class})
    public ResponseEntity<Object>candidateAlreadyExist(BankException bankException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bankException.getMessage());
    }
}

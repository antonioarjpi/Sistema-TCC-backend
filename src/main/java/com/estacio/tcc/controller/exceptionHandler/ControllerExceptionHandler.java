package com.estacio.tcc.controller.exceptionHandler;

import com.estacio.tcc.service.exceptions.AuthenticateErrorException;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.Locale;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.NOT_FOUND.value(), "Object not found exception.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(RuleOfBusinessException.class)
    public ResponseEntity<StandardError> ruleOfBusinessException(RuleOfBusinessException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Rule Of Business Exception.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthenticateErrorException.class)
    public ResponseEntity<StandardError> authenticateErrorException(AuthenticateErrorException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "AuthenticateErrorException.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StandardError> sqlException(SQLException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "SQLIntegrityConstraintViolationException", "Erro de validação (Pode existir itens filhos ativo)", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "MethodArgumentNotValidException", e.getFieldError().getDefaultMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}

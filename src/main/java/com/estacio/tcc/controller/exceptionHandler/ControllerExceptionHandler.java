package com.estacio.tcc.controller.exceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.estacio.tcc.service.exceptions.AuthenticateErrorException;
import com.estacio.tcc.service.exceptions.FileException;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.OffsetDateTime;

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
    public ResponseEntity<StandardError> authorization(AuthenticateErrorException e, HttpServletRequest request) {

        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.FORBIDDEN.value(), "AuthenticateErrorException", "Acesso Negado", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
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

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {

        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {

        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
        StandardError err = new StandardError(OffsetDateTime.now(), code.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(code).body(err);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {

        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {

        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro S3", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}

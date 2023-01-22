package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.exception.AccessDeniedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validationException(MethodArgumentNotValidException e){
        HashMap<String, Object> errAttribute = new LinkedHashMap<>();
        errAttribute.put("status", HttpStatus.BAD_REQUEST.value());
        errAttribute.put("error",HttpStatus.BAD_REQUEST.getReasonPhrase());
        errAttribute.put("timestamp", new Date().toString());
        List<String> validationList = e.getFieldErrors().stream().map(err -> err.getField() + ":" + err.getDefaultMessage()).collect(Collectors.toList());
        errAttribute.put("errors",validationList);
        return  errAttribute;
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateFormatFlagsException.class)
    public Map<String, Object> duplicateEntityExceptionHandler(){
        HashMap<String, Object> errAttribute = new LinkedHashMap<>();
        errAttribute.put("status", HttpStatus.CONFLICT.value());
        errAttribute.put("error",HttpStatus.CONFLICT.getReasonPhrase());
        errAttribute.put("message", "Duplicate entry found");
        errAttribute.put("timestamp", new Date().toString());
        return  errAttribute;
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, Object> accessDeniedExceptionHandler(){
        HashMap<String, Object> errAttribute = new LinkedHashMap<>();
        errAttribute.put("status", HttpStatus.FORBIDDEN.value());
        errAttribute.put("error",HttpStatus.FORBIDDEN.getReasonPhrase());
        errAttribute.put("message", "Access Denied");
        errAttribute.put("timestamp", new Date().toString());
        return  errAttribute;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Map<String, Object> emptyResultDataAccessExceptionHandler(){
        HashMap<String, Object> errAttribute = new LinkedHashMap<>();
        errAttribute.put("status", HttpStatus.NOT_FOUND.value());
        errAttribute.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
        errAttribute.put("message", "Entity not found");
        errAttribute.put("timestamp", new Date().toString());
        return  errAttribute;
    }
}

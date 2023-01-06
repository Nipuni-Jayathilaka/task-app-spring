package lk.ijse.dep9.app.advice;

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
}

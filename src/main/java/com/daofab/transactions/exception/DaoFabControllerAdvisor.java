package com.daofab.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class DaoFabControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DaoFabException.class)
    public ResponseEntity<Object> handleDaoFabException(
            DaoFabException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", getTime());
        body.put("message", ex.error);

        return new ResponseEntity<>(body, HttpStatus.valueOf(ex.responseCode));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            IOException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", getTime());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return  dtf.format(now);
    }
}

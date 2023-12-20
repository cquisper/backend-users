package com.backend.users.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, Object>> resourceDuplicate(RuntimeException runtimeException){
        Map<String, Object> response = Map.of("mensaje", runtimeException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);//500
    }
}
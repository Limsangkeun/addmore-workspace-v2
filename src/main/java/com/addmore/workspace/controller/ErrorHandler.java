package com.addmore.workspace.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
@Log4j2
public class ErrorHandler {

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class, RuntimeException.class})
    public ResponseEntity<Map<String, Object>> InternalErrorHandler(Exception e) {
        Map<String, Object> resultMap = new HashMap<>();
        log.info(e.getMessage());
        resultMap.put("msg", e.getLocalizedMessage());
        return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.ss.scrumptious_restaurant.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    public static final String ERROR_KEY = "error";
    public static final String STATUS_KEY = "status";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String, Object> handleNoSuchElementExceptions(NoSuchElementException ex) {
        log.error(ex.getMessage());
        return baseResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, Object> handleIllegalStateExceptions(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return baseResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> baseResponse(String errorMsg, HttpStatus status) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put(ERROR_KEY, errorMsg);
        response.put(STATUS_KEY, status.value());
        return response;
    }
}

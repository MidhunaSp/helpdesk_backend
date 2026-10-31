package com.springboot.helpdesk.config;

import com.springboot.helpdesk.dto.response.ErrorRespDto;
import com.springboot.helpdesk.exception.InactiveUserException;
import com.springboot.helpdesk.exception.InvalidCallException;
import com.springboot.helpdesk.exception.OwnershipException;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger("GlobalExceptionHandler.class");

    // returns always a json object not a string
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorRespDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("Id not found in DB");
             return ResponseEntity.badRequest().body(
                     new ErrorRespDto(e.getMessage(),
                             "ID is not in DB",
                                            Instant.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        Map<String,String> map = new HashMap<>();

        BindingResult bindingResult =  e.getBindingResult();
        List<FieldError> list = bindingResult.getFieldErrors();
        list.forEach(fieldError -> {
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                    log.warn("Field {} , failed validation: {}", fieldError.getField(),fieldError.getDefaultMessage());
                }
        );

        return ResponseEntity
                .badRequest()
                .body(map );
    }

    @ExceptionHandler(InvalidCallException.class)
    public ResponseEntity<ErrorRespDto> handleInvalidCallException(InvalidCallException e){
        //always reply the object not the string
        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorRespDto(e.getMessage(),
                                "Call is Invalid due to malformed data",
                                Instant.now())
                );
    }

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<ErrorRespDto> handleOwnershipException(
            OwnershipException e
    ){
        //always reply the object not the string

        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorRespDto(e.getMessage(),
                                "Logged-in User cannot perform this operation",
                                Instant.now())
                );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespDto> handleException(
            Exception e
    ){
        //always reply the object not the string

        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorRespDto(e.getMessage(),
                                "Invalid data passed",
                                Instant.now())
                );
    }
    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<ErrorRespDto> handleInactiveUserException(InactiveUserException e){
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(
                        new ErrorRespDto(e.getMessage(),
                                "Reach to ADMIN admin@helpdesk.com",
                                            Instant.now())
                );
    }
}

/*
.body(
     new ErrorRespDto(e.getMessage(),
     "Validation failed",
      Instant.now())
 );
 here... return type <ErrorRespDto>
 */
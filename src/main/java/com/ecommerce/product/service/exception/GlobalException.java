package com.ecommerce.product.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({AccessDeniedException.class,
                       BadCredentialsException.class})
    public ResponseEntity<ErrorDto> handleUnauthorized (RuntimeException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
            body(new ErrorDto(exception.getMessage(),HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler({UserNotFoundException.class,
                       CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFound (RuntimeException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ErrorDto(exception.getMessage(),HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({DuplicateUserException.class,
                       ProductNotFoundException.class,
                       CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleBadRequest(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ErrorDto(exception.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneric(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Something went wrong: " + exception.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}

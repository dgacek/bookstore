package com.softwaremind.bookstore.exception;

import com.softwaremind.bookstore.model.dto.ApiErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ObjectNotFoundException.class,
            ObjectAlreadyExistsException.class,
            InvalidArgumentException.class
    })
    @Nullable
    public ResponseEntity<ApiErrorDTO> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiErrorDTO(ex.getMessage()));
    }
}

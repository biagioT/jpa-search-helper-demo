package app.tozzi.controller.exception;

import app.tozzi.controller.model.ServiceError;
import app.tozzi.exception.JPASearchException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {JPASearchException.class})
    protected ResponseEntity<Object> handleException(JPASearchException me, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ServiceError(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), me.getMessage(), request.getRequestURI()));
    }
}

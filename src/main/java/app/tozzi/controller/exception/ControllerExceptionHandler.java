package app.tozzi.controller.exception;

import app.tozzi.exception.JPASearchException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {JPASearchException.class})
    protected ResponseEntity<ProblemDetail> handleException(JPASearchException me) {
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), me.getMessage()));
    }
}

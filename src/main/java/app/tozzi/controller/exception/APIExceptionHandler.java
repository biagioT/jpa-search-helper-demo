package app.tozzi.controller.exception;

import app.tozzi.exception.JPASearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {JPASearchException.class})
    protected ResponseEntity<ProblemDetail> handleException(JPASearchException me) {
        log.error("Controller Advice", me);
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), me.getMessage()));
    }
}

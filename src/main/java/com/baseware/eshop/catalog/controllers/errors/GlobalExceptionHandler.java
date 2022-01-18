package com.baseware.eshop.catalog.controllers.errors;

import com.baseware.eshop.catalog.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Problem> handleResourceNotFound(ResourceNotFound notFound) {
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Problem
           .builder()
           .withDetail(notFound.getMessage())
           .withStatus(Status.NOT_FOUND)
           .with("msg_i18n", "resource.not.found")
           .withTitle("Resource not found").build());
    }
}
package com.lime.lime.shop.exceptionHandler;

import com.lime.lime.shop.exceptionHandler.exception.ResourceAlreadyExistsException;
import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.exceptionHandler.exception.WrongFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Object> handlerIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Object> handlerIllegalState(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<Object> handlerForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    ResponseEntity<Object> handlerNotAuthorizedException(NotAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NotAuthorizedException");
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    ResponseEntity<Object> handlerItemExistException(ResourceAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ResourceNotExistsException.class)
    ResponseEntity<Object> handlerItemNotException(ResourceNotExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(WrongFormatException.class)
    ResponseEntity<Object> handlerWrongFormatException(WrongFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MessagingException.class)
    ResponseEntity<Object> errorWithMailSender(MessagingException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Problem with sending email");
    }

}

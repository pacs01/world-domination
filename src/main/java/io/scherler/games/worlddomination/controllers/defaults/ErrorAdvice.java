package io.scherler.games.worlddomination.controllers.defaults;

import io.scherler.games.worlddomination.exceptions.IllegalTurnException;
import io.scherler.games.worlddomination.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String resourceNotFoundHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class, IllegalTurnException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequestHandler(Exception ex) {
        return ex.getMessage();
    }
}

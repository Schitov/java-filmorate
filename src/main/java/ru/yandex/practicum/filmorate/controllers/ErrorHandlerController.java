package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.exceptions.ErrorResponse;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectParameterException(final ValidException e) {
        return new ErrorResponse(
                "Valid error", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExistenceOfObjects(final ExistenceOfObjectException e) {
        return new ErrorResponse(
                "Existence error", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleExistenceOfObjects(final RuntimeException e) {
        return new ErrorResponse(
                "Other error", e.getMessage()
        );
    }
}
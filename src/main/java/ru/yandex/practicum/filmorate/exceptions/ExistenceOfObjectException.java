package ru.yandex.practicum.filmorate.exceptions;

public class ExistenceOfObjectException extends RuntimeException {
    public ExistenceOfObjectException(String message) {
        super(message);
    }
}

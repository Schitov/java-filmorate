package ru.yandex.practicum.filmorate.exceptions;

public class ExistenceOfObject extends RuntimeException {
    public ExistenceOfObject(String message) {
        super(message);
    }
}

package ru.yandex.practicum.filmorate.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

@Component
public class ModelValidator implements Validator {
    private final static LocalDate BIRTHDAY_OF_FILMS = LocalDate.of(1895, 12, 28);

    @Override
    public boolean supports(Class<?> aClass) {
        if (Film.class.equals(aClass))
            return Film.class.equals(aClass);
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        System.out.println(errors);
        if (o.getClass() == Film.class) {
            Film film = (Film) o;
            filmValidate(film);
        } else if (o.getClass() == User.class) {
            User user = (User) o;
            userValidate(user);
        }
    }

    public void filmValidate(Film film) {
        if (film.getName() == null || film.getName().trim().length() == 0) {
            throw new ValidException("Name of film is empty");
        }

        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidException("Length of description more than 200 chars, please change it");
        }

        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(BIRTHDAY_OF_FILMS)) {
            throw new ValidException("The film cannot be made before 28.12.1895");
        }

        if (film.getDuration() <= 0) {
            throw new ValidException("Duration must be more 0");
        }
    }

    public void userValidate(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidException("Login musn't contain spaces");
        }
        if (!user.getBirthday().isBefore(LocalDate.now())) {
            throw new ValidException("This birthday is not existed");
        }
    }

    public void objectPresenceValidate(int id, HashMap<Integer, ?> objects) {
        if (!objects.containsKey(id)) {
            throw new ExistenceOfObjectException("Object with number " + id + " is not existed");
        }
    }
}
package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObject;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    private ModelValidator userValidator;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.inMemoryUserStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return inMemoryUserStorage.showUsers();
    }

    public void clear() {
        inMemoryUserStorage.clear();
    }

    public User getUser(Long id) {
        if (checkPresenceAndPositiveOfValue(id)) {
            userValidator.presentUserValidateById(id.intValue(), inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.getUser(id);
        }
        throw new ValidException("Id of user must be more than 0");
    }

    public void addUser(User user) {
        Errors errors = new BeanPropertyBindingResult(user, "film");
        userValidator.validate(user, errors);
        inMemoryUserStorage.addUser(user);
    }

    public void updateUser(User user) {
        Errors errors = new BeanPropertyBindingResult(user, "film");
        userValidator.validate(user, errors);
        userValidator.presentUserValidate(user, inMemoryUserStorage.getUsers());
        inMemoryUserStorage.updateUser(user);
    }

    public Set<Long> addFriend(Long idUser, Long idFriend) {
        if (checkPresenceAndPositiveOfValues(idUser, idFriend)) {
            userValidator.presentUserValidateById(idUser.intValue(), inMemoryUserStorage.getUsers());
            userValidator.presentUserValidateById(idFriend.intValue(), inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.addFriend(idUser, idFriend);
        }
        throw new ExistenceOfObject("idFilm or idUser must be more than 0");
    }

    public Set<Long> deleteFriend(Long idUser, Long idFriend) {
        if (checkPresenceAndPositiveOfValues(idUser, idFriend)) {
            userValidator.presentUserValidateById(idUser.intValue(), inMemoryUserStorage.getUsers());
            userValidator.presentUserValidateById(idFriend.intValue(), inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.deleteFriend(idUser, idFriend);
        }
        throw new ValidException("idFilm or idUser must be more than 0");
    }

    public List<User> getFriends(Long userId) {
        if (checkPresenceAndPositiveOfValue(userId)) {
            userValidator.presentUserValidateById(userId.intValue(), inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.showFriends(userId);
        }
        throw new ValidException("idUser must be more than 0");
    }

    public List<User> showCommonFriends(Long idUser, Long otherId) {
        if (checkPresenceAndPositiveOfValues(idUser, otherId)) {
            userValidator.presentUserValidateById(idUser.intValue(), inMemoryUserStorage.getUsers());
            userValidator.presentUserValidateById(otherId.intValue(), inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.showCommonFriends(idUser, otherId);
        }
        throw new ValidException("id of user must be more than 0");
    }

    public boolean checkPresenceAndPositiveOfValues(Long idUser, Long idFilm) {
        if (idUser != null & idFilm != null & idUser >= 0 & idFilm >= 0) {
            return true;
        }
        return false;
    }

    public boolean checkPresenceAndPositiveOfValue(Long id) {
        if (id != null & id >= 0) {
            return true;
        }
        return false;
    }
}

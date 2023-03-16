package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
//import ru.yandex.practicum.filmorate.storage.ImMemoryUserStorage;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    UserStorage inMemoryUserStorage;
    @Autowired
    private ModelValidator userValidator;

    @Autowired
    public UserService(UserDBStorage userStorage) {
        this.inMemoryUserStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return inMemoryUserStorage.showUsers();
    }

    public User getUser(long id) {
        if (checkPresenceAndPositiveOfValue(id)) {
            userValidator.objectPresenceValidate(id, inMemoryUserStorage.getUsers());
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
        userValidator.objectPresenceValidate(user.getId(), inMemoryUserStorage.getUsers());
        inMemoryUserStorage.updateUser(user);
    }

    public Set<Long> addFriend(long idUser, long idFriend) {
        if (checkPresenceAndPositiveOfValues(idUser, idFriend)) {
            userValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
            userValidator.objectPresenceValidate(idFriend, inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.addFriend(idUser, idFriend);
        }
        throw new ExistenceOfObjectException("idFilm or idUser must be more than 0");
    }

    public Set<Long> deleteFriend(long idUser, long idFriend) {
        if (checkPresenceAndPositiveOfValues(idUser, idFriend)) {
            userValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
            userValidator.objectPresenceValidate(idFriend, inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.deleteFriend(idUser, idFriend);
        }
        throw new ValidException("idFilm or idUser must be more than 0");
    }

    public List<User> getFriends(long userId) {
        if (checkPresenceAndPositiveOfValue(userId)) {
            userValidator.objectPresenceValidate(userId, inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.showFriends(userId);
        }
        throw new ValidException("idUser must be more than 0");
    }

    public List<User> showCommonFriends(long idUser, long otherId) {
        if (checkPresenceAndPositiveOfValues(idUser, otherId)) {
            userValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
            userValidator.objectPresenceValidate(otherId, inMemoryUserStorage.getUsers());
            return inMemoryUserStorage.showCommonFriends(idUser, otherId);
        }
        throw new ValidException("id of user must be more than 0");
    }

    public boolean checkPresenceAndPositiveOfValues(long idUser, long idFilm) {
        if (idUser >= 0 & idFilm >= 0) {
            return true;
        }
        return false;
    }

    public boolean checkPresenceAndPositiveOfValue(long id) {
        if (id >= 0) {
            return true;
        }
        return false;
    }
}

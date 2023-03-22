package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.GenreDAOStorage;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.List;

@Service
public class GenreService {

    private GenreDAOStorage genreDAOStorage;

    private ModelValidator genreValidator;

    @Autowired
    public GenreService(GenreDAOStorage genreDAOStorage, ModelValidator genreValidator) {
        this.genreDAOStorage = genreDAOStorage;
        this.genreValidator = genreValidator;
    }

    public List<Genre> getGenres() {
        return genreDAOStorage.getGenres();
    }

    public Genre getGenreById(int id) {
        genreValidator.objectPresenceValidate(id, genreDAOStorage.getIds());
        return genreDAOStorage.getGenreById(id);
    }
}

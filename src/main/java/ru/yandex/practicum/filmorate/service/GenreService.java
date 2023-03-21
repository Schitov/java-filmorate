package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDAOStorage;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    GenreDAOStorage genreDAOStorage;

    @Autowired
    private ModelValidator genreValidator;

    @Autowired
    public GenreService(GenreDAOStorage genreDAOStorage) {
        this.genreDAOStorage = genreDAOStorage;
    }

    public List<Genre> getGenres() {
        return genreDAOStorage.getGenres();
    }

    public Genre getGenreById(int id) {
        genreValidator.objectPresenceValidate(id, genreDAOStorage.getIds());
        return genreDAOStorage.getGenreById(id);
    }
}

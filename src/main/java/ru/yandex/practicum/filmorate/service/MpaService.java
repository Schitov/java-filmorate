package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.MpaDAOStorage;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.List;

@Service
public class MpaService {

    MpaDAOStorage mpaDAOStorage;

    @Autowired
    private ModelValidator mpaValidator;

    @Autowired
    public MpaService(MpaDAOStorage mpaDAOStorage) {
        this.mpaDAOStorage = mpaDAOStorage;
    }

    public List<MPA> getMpas() {
        return mpaDAOStorage.getMpas();
    }

    public MPA getGenreById(int id) {
        mpaValidator.objectPresenceValidate(id, mpaDAOStorage.getIds());
        return mpaDAOStorage.getMpaById(id);
    }

}

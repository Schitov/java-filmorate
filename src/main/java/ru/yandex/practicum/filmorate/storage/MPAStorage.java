package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPAStorage {

    public List<MPA> getMpas();

    public MPA getMpaById(int id);

    public List<Long> getIds();

}

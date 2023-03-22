package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {

    @Autowired
    private MpaService mpaService;

    @GetMapping()
    public List<MPA> showAllMpas() {
        return mpaService.getMpas();
    }

    @GetMapping("/{Rating_ID}")
    public MPA showGenreById(@PathVariable("Rating_ID") int mpaId) {
        return mpaService.getGenreById(mpaId);
    }
}

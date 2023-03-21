package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Film {
    long id;
    @NotNull(message = "Name must be filled")
    @NotBlank(message = "Name must be filled")
    private String name;
    private String description;
    @NotNull(message = "Date must be filled")
    private LocalDate releaseDate;
    @Positive(message = "Duration must be more than 0")
    private long duration;
    private int rate;
    private MPA mpa;
    private List<Genre> genres = new ArrayList<>();
    private Set<Long> likes;

    public Film(String name, String description,
                LocalDate releaseDate, long duration, MPA ratingMpa, int rate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = ratingMpa;
        this.rate = rate;
    }

    public Film(long id, String name, String description,
                LocalDate releaseDate, long duration, MPA ratingMpa, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = ratingMpa;
        this.rate = rate;
    }
}

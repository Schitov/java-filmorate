package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Genre {

    int id;
    String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(int id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;

        return Objects.equals(genre.getId(), this.getId());
    }

}

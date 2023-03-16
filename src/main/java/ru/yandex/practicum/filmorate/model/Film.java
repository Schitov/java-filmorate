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
import java.util.HashSet;
import java.util.Set;

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
    private Set<Long> likes;

    public Film(long filmId, String name, String description, LocalDate releaseDate, long duration) {
        this.id = filmId;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int countOfLikes() {
        if (likes == null) {
            likes = new HashSet<>();
            return 0;
        }
        return likes.size();
    }

    public void addLike(long idUser) {
        if (likes == null) {
            likes = new HashSet<>();
            likes.add(Long.valueOf(idUser));
            return;
        }
        likes.add(Long.valueOf(idUser));
    }

    public void removeLike(Long idUser) {
        likes.remove(idUser);
    }
}

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

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Film {
    int id;
    @NotNull(message = "Name must be filled")
    @NotBlank(message = "Name must be filled")
    private String name;
    private String description;
    @NotNull(message = "Date must be filled")
    private LocalDate releaseDate;
    @Positive(message = "Duration must be more than 0")
    private long duration;

}

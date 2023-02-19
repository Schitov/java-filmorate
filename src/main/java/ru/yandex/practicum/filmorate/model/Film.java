package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@ToString
public class Film extends Item {
    @NotNull(message = "Name must be filled")
    @NotBlank(message = "Name must be filled")
    private String name;
    @Size(max = 200, message = "Value of description must be no more than 200 chars")
    private String description;
    @NotNull(message = "Date must be filled")
    private LocalDate releaseDate;
    @Positive(message = "Duration must be more than 0")
    private long duration;

}

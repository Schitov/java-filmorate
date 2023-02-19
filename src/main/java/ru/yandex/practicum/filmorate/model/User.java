package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@ToString
public class User extends Item {
    @Email
    private String email;
    @NonNull
    @NotBlank
    @NotEmpty
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}

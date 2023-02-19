package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
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

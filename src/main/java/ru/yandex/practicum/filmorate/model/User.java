package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@ToString
public class User {
    int id;
    @Email
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private Set<Long> friends;

    public void addFriend(Long id) {
        if (friends == null) {
            friends = new HashSet<Long>();
            friends.add(id);
            return;
        }
        friends.add(id);
    }

    public void deleteFriend(Long id) {
        if (friends == null) {
            return;
        } else friends.remove(id);
    }

    public Set<Long> getFriends() {
        if (friends == null) {
            friends = new HashSet<>();
        }
        return friends;
    }
}

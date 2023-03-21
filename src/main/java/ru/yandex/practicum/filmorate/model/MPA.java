package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MPA {

    int id;
    String name;


    public MPA(int id) {
        this.id = id;
    }
}
